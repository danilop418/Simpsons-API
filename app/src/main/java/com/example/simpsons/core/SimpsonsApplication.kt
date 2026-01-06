package com.example.simpsons.core

import android.app.Application
import com.example.simpsons.core.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class SimpsonsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SimpsonsApplication)
            modules(AppModule().module)
        }
    }
}