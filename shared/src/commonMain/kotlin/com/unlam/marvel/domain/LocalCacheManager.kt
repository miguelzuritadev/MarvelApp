package com.unlam.marvel.domain

import com.unlam.marvel.preferences.IDataStoreRepository
import kotlinx.coroutines.flow.first

/**
 * By default it use 2 minutes as time to live (ttl)
 */
class LocalCacheManager(val dataStoreRepository: IDataStoreRepository, val ttl:Long = 1000 * 60 * 2) {

    suspend fun useCache(currentTime: Long): Boolean {
        val dataStoreTimestamp = dataStoreRepository.readTimestamp().first()

        val differenceMs = currentTime - dataStoreTimestamp
        println("difference ${differenceMs/1000} seconds")

        return (differenceMs < ttl)
    }

    suspend fun saveTimestamp(timestamp: Long) {
        dataStoreRepository.saveTimestamp(timestamp)
    }
}