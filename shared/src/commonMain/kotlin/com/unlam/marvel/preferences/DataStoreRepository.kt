package com.unlam.marvel.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val dataStore: DataStore<Preferences>) : IDataStoreRepository {
    companion object {
        val TIMESTAMP_KEY = longPreferencesKey(name = "timestamp_key")
    }

    override suspend fun saveTimestamp(timestamp: Long): Boolean {
        return try {
            dataStore.edit { preferences ->
                preferences.set(key = TIMESTAMP_KEY, value = timestamp)
            }
            true
        } catch (e: Exception) {
            println("saveTimestamp() Error: $e")
            false
        }
    }

    override fun readTimestamp(): Flow<Long> {
        return dataStore.data
            .catch { emptyFlow<Long>() }
            .map { preferences ->
                preferences[TIMESTAMP_KEY] ?: 0L
            }
    }
}