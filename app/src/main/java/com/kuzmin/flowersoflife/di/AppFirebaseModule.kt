package com.kuzmin.flowersoflife.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kuzmin.flowersoflife.core.AuthService
import com.kuzmin.flowersoflife.data.FirebaseAuthServiceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppFirebaseModule {

    @Binds
    @Singleton
    fun bindAuthService(firebaseAuthServiceImpl: FirebaseAuthServiceImpl): AuthService

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

        @Provides
        @Singleton
        fun provideFirebaseDatabase(): FirebaseDatabase {
            return FirebaseDatabase.getInstance()
        }
    }
}