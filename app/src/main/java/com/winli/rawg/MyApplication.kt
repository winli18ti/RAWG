@file:Suppress("unused")

package com.winli.rawg

import android.app.Application
import com.winli.rawg.core.di.databaseModule
import com.winli.rawg.core.di.networkModule
import com.winli.rawg.core.di.repositoryModule
import com.winli.rawg.di.useCaseModule
import com.winli.rawg.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}