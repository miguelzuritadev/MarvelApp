package com.unlam.marvel.preferences

import kotlinx.coroutines.flow.Flow

interface IDataStoreRepository {
    suspend fun saveTimestamp(timestamp: Long): Boolean
    fun readTimestamp(): Flow<Long>
}