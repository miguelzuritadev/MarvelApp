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
    fun useCache_within_ttl_should_return_true() = runTest {
        // Arrange
        val currentTime = 1730553215L
        fakeDataStoreRepository.saveTimestamp(currentTime - 1000) // 1 second ago

        // Act
        val result = localCacheManager.useCache(currentTime)

        // Assert
        assertTrue(result)
    }

    @Test
    fun useCache_outside_ttl_should_return_false() = runTest {
        // Arrange
        val currentTime = 1730553215L
        fakeDataStoreRepository.saveTimestamp(currentTime - 1000 * 60 * 3) // 3 minutes ago

        // Act
        val result = localCacheManager.useCache(currentTime)

        // Assert
        assertFalse(result)
    }

    @Test
    fun saveTimestamp_should_save_the_given_timestamp() = runTest {
        // Arrange
        val timestamp = 1730553215L

        // Act
        localCacheManager.saveTimestamp(timestamp)
        val savedTimestamp = fakeDataStoreRepository.readTimestamp().first()

        // Assert
        assertEquals(timestamp, savedTimestamp)
    }
}