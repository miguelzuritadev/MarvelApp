package com.miguelzurita.marveltvapp

import android.app.Application
import com.unlam.marvel.di.commonModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TVApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidLogger(level = if (BuildConfig.DEBUG) Level.INFO else Level.NONE)
            androidContext(this@TVApp)
            modules(commonModule())
        }
    }
}