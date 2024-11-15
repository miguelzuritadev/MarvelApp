package com.unlam.marvel.di

import com.unlam.marvel.DatabaseDriverFactory
import com.unlam.marvel.createDatabase
import com.unlam.marvel.data.local.LocalCharacterDBRepositoryImp
import com.unlam.marvel.data.local.ILocalCharacterRepository
import com.unlam.marvel.data.network.IMarvelRepository
import com.unlam.marvel.data.network.MarvelApiClientImpl
import com.unlam.marvel.data.network.MarvelRepositoryImpl
import com.unlam.marvel.domain.LocalCacheManager
import com.unlam.marvel.preferences.DataStoreRepository
import com.unlam.marvel.preferences.DataStoreWrapper
import com.unlam.marvel.preferences.IDataStoreRepository
import com.unlam.marvel.ui.AppViewModel
import org.koin.dsl.module

fun commonModule() = module {

    single<IDataStoreRepository> { DataStoreRepository(DataStoreWrapper().resolveDataStore()) }

    single { LocalCacheManager(get()) }

    single<ILocalCharacterRepository> { LocalCharacterDBRepositoryImp(createDatabase(DatabaseDriverFactory())) }

    single<IMarvelRepository> { MarvelRepositoryImpl(MarvelApiClientImpl()) }

    single { AppViewModel(get(), get()) }
}