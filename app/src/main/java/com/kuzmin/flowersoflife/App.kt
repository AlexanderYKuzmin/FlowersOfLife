package com.kuzmin.flowersoflife

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kuzmin.flowersoflife.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}