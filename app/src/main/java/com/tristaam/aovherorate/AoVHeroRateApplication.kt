package com.tristaam.aovherorate

import android.app.Application
import com.tristaam.aovherorate.di.appModule
import com.tristaam.aovherorate.di.databaseModule
import com.tristaam.aovherorate.di.networkModule
import com.tristaam.aovherorate.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AoVHeroRateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AoVHeroRateApplication)
            modules(databaseModule, networkModule, repositoryModule, appModule)
        }
    }
}