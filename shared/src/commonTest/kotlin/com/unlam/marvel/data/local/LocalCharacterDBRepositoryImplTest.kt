package com.unlam.marvel.data.local

import com.unlam.marvel.Database
import com.unlam.marvel.DatabaseDriverFactory
import com.unlam.marvel.createDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class LocalCharacterDBRepositoryImplTest {
    private lateinit var repository: LocalCharacterDBRepositoryImp
    private lateinit var database: Database

    @BeforeTest
    fun setUp() {
        database = createDatabase(DatabaseDriverFactory())
        repository = LocalCharacterDBRepositoryImp(database)
    }

    private suspend fun populateDatabaseWithFakeNumberOfCharacters(numberOfCharacters: Int = 2): List<LocalCharacter> {
        repository.deleteAll()
        val characterList = mutableListOf<LocalCharacter>()
        for (i in 1..numberOfCharacters) {
            val character = LocalCharacter(i.toLong(), "name$i", "description$i", "url$i")
            repository.insert(character)
            characterList.add(character)
        }
        return characterList
    }

    @Test
    fun getBy_should_return_the_inserted_character() = runTest {
        // Arrange
        val expected = populateDatabaseWithFakeNumberOfCharacters()[0]

        // Act
        val result = repository.getBy(1)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun getAll_should_return_all_inserted_characters() = runTest {
        // Arrange
        val characterList = populateDatabaseWithFakeNumberOfCharacters()

        // Act
        val result = repository.getAll()

        // Assert
        assertEquals(characterList, result)
    }


    @Test
    fun delete_should_remove_the_character_from_database() = runTest {
        // Arrange
        populateDatabaseWithFakeNumberOfCharacters(1)

        // Act
        repository.delete(1)
        val result = repository.getAll()

        // Assert
        assertEquals(emptyList(), result)
    }

    @Test
    fun deleteAll_should_remove_all_characters_from_database() = runTest {
        // Arrange
        populateDatabaseWithFakeNumberOfCharacters()

        // Act
        repository.deleteAll()
        val result = repository.getAll()

        // Assert
        assertEquals(emptyList(), result)
    }
}