package com.unlam.marvel.data.local

import com.unlam.marvel.CharactersQueries
import com.unlam.marvel.Database


class LocalCharacterDBRepositoryImp(db: Database) : ILocalCharacterRepository {

    private val query: CharactersQueries = db.charactersQueries

    override suspend fun getAll(): List<LocalCharacter> {
        return query.getAllCharacters(::mapToLocalCharacter).executeAsList()
    }

    override suspend fun getBy(id: Long): LocalCharacter {
        return query.getCharacter(id, ::mapToLocalCharacter).executeAsOne()
    }

    override suspend fun insert(character: LocalCharacter) {
        // Check if the character already exists
        val existingCharacter = query.getCharacter(character.id, ::mapToLocalCharacter).executeAsOneOrNull()
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

    private fun mapToLocalCharacter(id: Long, name: String, description: String?, thumbnailUrl: String?): LocalCharacter {
        return LocalCharacter(id, name, description, thumbnailUrl)
    }
}