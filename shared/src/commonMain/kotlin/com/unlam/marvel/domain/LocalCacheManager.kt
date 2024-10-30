package com.unlam.marvel.domain

import com.unlam.marvel.preferences.DataStoreRepository
import kotlinx.coroutines.flow.first

class LocalCacheManager(val dataStoreRepository: DataStoreRepository) {

    private val ttl = 1000 * 60 * 2 // 2 minutes

    suspend fun useCache(currentTime: Long): Boolean {
        val dataStoreTimestamp = dataStoreRepository.readTimestamp().first()
        println("==timestamp: $dataStoreTimestamp==")

        val differenceMs = currentTime - dataStoreTimestamp
        println("differenceMs: $differenceMs")

        return (differenceMs < ttl)
    }

    suspend fun saveTimestamp(timestamp: Long) {
        dataStoreRepository.saveTimestamp(timestamp)
    }
}