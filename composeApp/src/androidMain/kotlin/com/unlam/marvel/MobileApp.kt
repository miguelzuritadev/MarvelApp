package com.unlam.marvel

import android.app.Application
import com.unlam.marvel.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MobileApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@MobileApp)
            modules(commonModule())
        }
    }
}