package com.unlam.marvel.network

interface CharactersRepository {
    suspend fun getCharacters(timestamp: Long, md5: String): List<Character>
}