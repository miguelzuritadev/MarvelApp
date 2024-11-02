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
    fun testInsertAndGetCharacter() = runTest {
        repository.deleteAll()
        // Given
        val character = LocalCharacter(1, "name", "description", "url")

        // When
        repository.insert(character)
        val result = repository.getBy(1)

        // Then
        assertEquals(character, result)
    }

    @Test
    fun testGetAllCharacters() = runTest {
        repository.deleteAll()
        // Given
        val character1 = LocalCharacter(1, "name1", "description1", "url1")
        val character2 = LocalCharacter(2, "name2", "description2", "url2")
        repository.insert(character1)
        repository.insert(character2)

        // When
        val result = repository.getAll()

        // Then
        assertEquals(listOf(character1, character2), result)
    }

    @Test
    fun testDeleteCharacter() = runTest {
        repository.deleteAll()
        // Given
        val character = LocalCharacter(1, "name", "description", "url")
        repository.insert(character)

        // When
        repository.delete(1)
        val result = repository.getAll()

        // Then
        assertEquals(emptyList(), result)
    }

    @Test
    fun testDeleteAllCharacters() = runTest {
        // Given
        val character1 = LocalCharacter(1, "name1", "description1", "url1")
        val character2 = LocalCharacter(2, "name2", "description2", "url2")
        repository.insert(character1)
        repository.insert(character2)

        // When
        repository.deleteAll()
        val result = repository.getAll()

        // Then
        assertEquals(emptyList(), result)
    }
}