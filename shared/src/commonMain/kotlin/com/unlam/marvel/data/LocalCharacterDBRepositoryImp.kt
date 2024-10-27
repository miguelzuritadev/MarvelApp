package com.unlam.marvel.data

import com.unlam.marvel.CharactersQueries
import com.unlam.marvel.Database


class LocalCharacterDBRepositoryImp(db: Database) : LocalCharacterRepository {

    private val query: CharactersQueries = db.charactersQueries

    override suspend fun getAll(): List<LocalCharacter> {
        return query.getAllCharacters(::mapToCharacter).executeAsList()
    }

    override suspend fun getBy(id: Long): LocalCharacter {
        return query.getCharacter(id, ::mapToCharacter).executeAsOne()
    }

    override suspend fun insert(character: LocalCharacter) {
        // Check if the character already exists
        val existingCharacter = query.getCharacter(character.id, ::mapToCharacter).executeAsOneOrNull()
        if (existingCharacter == null) {
            query.insertCharacter(character.id, character.name, character.description, character.url)
        } else {
            query.updateCharacter(character.name, character.description, character.url, character.id)
        }
    }

    override suspend fun deleteAll() {
        query.deleteAllCharacters()
    }

    override suspend fun delete(id: Long) {
        query.deleteCharacter(id)
    }

    private fun mapToCharacter(id: Long, name: String, description: String?, thumbnailUrl: String?): LocalCharacter {
        return LocalCharacter(id, name, description, thumbnailUrl)
    }
}