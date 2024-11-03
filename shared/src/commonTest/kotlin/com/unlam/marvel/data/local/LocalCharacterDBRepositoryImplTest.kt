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

    @Test
    fun getBy_should_return_the_inserted_character() = runTest {
        // Arrange
        repository.deleteAll()
        val expected = LocalCharacter(1, "name", "description", "url")
        repository.insert(expected)

        // Act
        val result = repository.getBy(1)

        // Assert
        assertEquals(expected, result)
    }

    @Test
    fun getAll_should_return_all_inserted_characters() = runTest {
        repository.deleteAll()
        // Arrange
        val character1 = LocalCharacter(1, "name1", "description1", "url1")
        val character2 = LocalCharacter(2, "name2", "description2", "url2")
        repository.insert(character1)
        repository.insert(character2)

        // Act
        val result = repository.getAll()

        // Assert
        assertEquals(listOf(character1, character2), result)
    }

    @Test
    fun delete_should_remove_the_character_from_database() = runTest {
        repository.deleteAll()
        // Arrange
        val character = LocalCharacter(1, "name", "description", "url")
        repository.insert(character)

        // Act
        repository.delete(1)
        val result = repository.getAll()

        // Assert
        assertEquals(emptyList(), result)
    }

    @Test
    fun deleteAll_should_remove_all_characters_from_database() = runTest {
        // Arrange
        val character1 = LocalCharacter(1, "name1", "description1", "url1")
        val character2 = LocalCharacter(2, "name2", "description2", "url2")
        repository.insert(character1)
        repository.insert(character2)

        // Act
        repository.deleteAll()
        val result = repository.getAll()

        // Assert
        assertEquals(emptyList(), result)
    }
}