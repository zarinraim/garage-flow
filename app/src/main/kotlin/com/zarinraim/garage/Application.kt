package com.zarinraim.garage

import android.app.Application
import com.zarinraim.garage.di.AppGraph
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(AppGraph.module)
        }
    }
}