package com.kuzmin.flowersoflife.di

import com.google.firebase.auth.FirebaseAuth
import com.kuzmin.flowersoflife.core.AuthService
import com.kuzmin.flowersoflife.data.FirebaseAuthServiceImpl
import org.koin.dsl.module

val appFirebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single<AuthService> {
        FirebaseAuthServiceImpl(
            get()
        )
    }
}