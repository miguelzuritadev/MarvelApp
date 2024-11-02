package com.unlam.marvel.data.network

import com.unlam.marvel.domain.model.Character

class MarvelRepositoryImpl(val client: IMarvelApiClient) : IMarvelRepository {
    override suspend fun getCharacters(timestamp: Long, md5: String): List<Character> {
        return client.getCharacters(timestamp, md5).toModel()
    }
}