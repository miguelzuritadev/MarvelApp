package com.unlam.marvel.di

import com.unlam.marvel.DatabaseDriverFactory
import com.unlam.marvel.createDatabase
import com.unlam.marvel.data.local.LocalCharacterDBRepositoryImp
import com.unlam.marvel.data.local.LocalCharacterRepository
import com.unlam.marvel.domain.LocalCacheManager
import com.unlam.marvel.preferences.DataStoreRepository
import com.unlam.marvel.preferences.DataStoreWrapper
import com.unlam.marvel.ui.AppViewModel
import org.koin.dsl.module

fun commonModule() = module {

    single { DataStoreRepository(DataStoreWrapper().resolveDataStore()) }

    single {LocalCacheManager(get())}

    single<LocalCharacterRepository> { LocalCharacterDBRepositoryImp(createDatabase(DatabaseDriverFactory())) }

    single { AppViewModel(get()) }
}