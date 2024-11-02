package com.unlam.marvel.ui


import LocalCharacterDBRepositoryTest
import com.unlam.marvel.data.local.ILocalCharacterRepository
import com.unlam.marvel.data.network.IMarvelRepository
import com.unlam.marvel.data.network.MarvelApiClientTest
import com.unlam.marvel.data.network.MarvelRepositoryImpl
import com.unlam.marvel.domain.LocalCacheManager
import com.unlam.marvel.domain.model.Character
import com.unlam.marvel.preferences.DataStoreRepository
import com.unlam.marvel.preferences.DataStoreWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AppVIewModelTest:KoinTest {

    private lateinit var viewModel: AppViewModel

    // Koin module
    private val testModule = module {
        single { DataStoreRepository(DataStoreWrapper().resolveDataStore()) }
        single { LocalCacheManager(get()) }
        single<ILocalCharacterRepository> { LocalCharacterDBRepositoryTest() }
        single<IMarvelRepository> { MarvelRepositoryImpl(MarvelApiClientTest()) }
        single { AppViewModel(get(), get()) }
    }

    @BeforeTest
    fun setUp() {
        // Start Koin
        startKoin {
            modules(testModule)
        }
        viewModel = get() // Get the ViewModel instance
    }

    @AfterTest
    fun tearDown() {
        // Stop Koin
        stopKoin()
    }

    @Test
    fun testSaveToLocalAndFetchCharactersFromLocal() = runTest{
        // Given
        val character = Character(1, "name", "description", "image")
        val characters = listOf(character)
        viewModel.saveLocalCharacters(characters)

        // When
        viewModel.getCharactersFromLocal()
        runCurrent()
        val result = viewModel.items.value

        // Then
        assertEquals(characters, result)
    }

    @Test
    fun testGetCharactersFromNetworkAndSaveToLocalCache() =  runTest {
        // Given
        val expected = MarvelApiClientTest().charactersResponse.toModel()
        val timestamp = 1000L

        // When
        viewModel.getCharactersFromNetworkAndSaveToLocalCache(timestamp)
        runCurrent()
        val result = viewModel.items.value

        // Then
        assertEquals(expected, result)
    }
}