package com.unlam.marvel.network

interface IMarvelClient {
    suspend fun getCharacters(timestamp: Long, md5: String): CharactersResponse
}