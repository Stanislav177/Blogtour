package com.myblogtour.blogtour

import android.app.Application
import com.myblogtour.blogtour.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(Modules.networkModule, Modules.viewModelsModule,Modules.firebase,Modules.permissionModule)
        }
    }
}