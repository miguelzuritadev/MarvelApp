package com.unlam.marvel.data.network

interface IMarvelClient {
    suspend fun getCharacters(timestamp: Long, md5: String): CharactersResponse
}