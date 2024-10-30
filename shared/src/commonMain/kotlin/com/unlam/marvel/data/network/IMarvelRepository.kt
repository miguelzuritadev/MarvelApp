package com.unlam.marvel.data.network

import com.unlam.marvel.domain.model.Character

interface IMarvelRepository {
    suspend fun getCharacters(timestamp: Long, md5: String): List<Character>
}