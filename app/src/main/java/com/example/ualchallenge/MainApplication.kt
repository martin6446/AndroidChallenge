package com.example.ualchallenge

import android.app.Application
import com.example.ualchallenge.di.appModule
import com.example.ualchallenge.di.daoModule
import com.example.ualchallenge.di.provideRoomDataBaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                appModule,
                provideRoomDataBaseModule,
                daoModule
            )
        }
    }
}