package com.unlam.marvel.ui


import LocalCharacterDBRepositoryFake
import com.unlam.marvel.data.local.ILocalCharacterRepository
import com.unlam.marvel.data.network.IMarvelRepository
import com.unlam.marvel.data.network.MarvelApiClientFake
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
class AppVIewModelTest : KoinTest {

    private lateinit var viewModel: AppViewModel

    // Test Koin module
    private val testModule = module {
        single { DataStoreRepository(DataStoreWrapper().resolveDataStore()) }
        single { LocalCacheManager(get()) }
        single<ILocalCharacterRepository> { LocalCharacterDBRepositoryFake() }
        single<IMarvelRepository> { MarvelRepositoryImpl(MarvelApiClientFake()) }
        single { AppViewModel(get(), get()) }
    }

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(testModule)
        }
        viewModel = get()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getCharactersFromLocal_should_return_characters_from_local() = runTest {
        // Arrange
        val character = Character(1, "name", "description", "image")
        val characters = listOf(character)
        viewModel.saveLocalCharacters(characters)

        // Act
        viewModel.getCharactersFromLocal()
        runCurrent()
        val result = viewModel.items.value

        // Assert
        assertEquals(characters, result)
    }

    @Test
    fun getCharactersFromNetworkAndSaveToLocalCache_should_return_expected_characters() = runTest {
        // Arrange
        val expected = MarvelApiClientFake().charactersResponse.toModel()
        val timestamp = 1000L

        // Act
        viewModel.getCharactersFromNetworkAndSaveToLocalCache(timestamp)
        runCurrent()
        val result = viewModel.items.value

        // Assert
        assertEquals(expected, result)
    }
}