package com.unlam.marvel.data.local


interface ILocalCharacterRepository {
    suspend fun insert(character: LocalCharacter): Unit
    suspend fun deleteAll(): Unit
    suspend fun delete(id: Long): Unit
    suspend fun getAll(): List<LocalCharacter>
    suspend fun getBy(id: Long): LocalCharacter
}