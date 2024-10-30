package com.unlam.marvel.di

import com.unlam.marvel.ui.AppViewModel
import org.koin.dsl.module

fun commonModule() = module {
    single {
        AppViewModel()
    }
}