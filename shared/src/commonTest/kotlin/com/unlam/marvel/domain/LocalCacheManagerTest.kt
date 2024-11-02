package com.unlam.marvel.domain

import com.unlam.marvel.preferences.IDataStoreRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LocalCacheManagerTest {

    private lateinit var localCacheManager: LocalCacheManager
    private var fakeDataStoreRepository: IDataStoreRepository = object : IDataStoreRepository {
        private val data = MutableStateFlow(0L)

        override suspend fun saveTimestamp(timestamp: Long): Boolean {
            data.value = timestamp
            return true
        }

        override fun readTimestamp(): Flow<Long> {
            return data
        }

    }

    @BeforeTest
    fun setUp() {
        localCacheManager = LocalCacheManager(fakeDataStoreRepository)
    }

    @Test
    fun testUseCache_withinTTL() = runTest {
        // Given
        val currentTime = 1730553215L
        fakeDataStoreRepository.saveTimestamp(currentTime - 1000) // 1 second ago

        // When
        val result = localCacheManager.useCache(currentTime)

        // Then
        assertTrue(result)
    }

    @Test
    fun testUseCache_outsideTTL() = runTest {
        // Given
        val currentTime = 1730553215L
        fakeDataStoreRepository.saveTimestamp(currentTime - 1000 * 60 * 3) // 3 minutes ago

        // When
        val result = localCacheManager.useCache(currentTime)

        // Then
        assertFalse(result)
    }

    @Test
    fun testSaveTimestamp() = runTest {
        // Given
        val timestamp = 1730553215L

        // When
        localCacheManager.saveTimestamp(timestamp)
        val savedTimestamp = fakeDataStoreRepository.readTimestamp().first()

        // Then
        assertEquals(timestamp, savedTimestamp)
    }
}