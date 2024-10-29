package com.unlam.marvel.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.unlam.marvel.resolveDataStorePath
import okio.Path.Companion.toPath


class DataStoreWrapper {
    private var dataStore: DataStore<Preferences>? = null

    fun resolveDataStore(): DataStore<Preferences> {
        return getDataStore {
            resolveDataStorePath()
        }
    }

    fun getDataStore(producePath: () -> String): DataStore<Preferences> {
        return dataStore ?: run {
            PreferenceDataStoreFactory.createWithPath(
                produceFile = { producePath().toPath() }
            ).also { dataStore = it }
        }
    }
}