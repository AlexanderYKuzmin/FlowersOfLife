package com.kuzmin.flowersoflife

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}