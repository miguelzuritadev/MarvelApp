package com.unlam.marvel.data.network

interface IMarvelApiClient {
    suspend fun getCharacters(timestamp: Long, md5: String): CharactersResponse
}