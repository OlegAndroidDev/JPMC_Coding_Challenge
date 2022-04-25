package com.example.jpmc_coding_challenge.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@KoinApp)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    apiModule,
                    retrofitModule
                )
            )
        }
    }
}