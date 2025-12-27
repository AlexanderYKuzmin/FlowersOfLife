package com.kuzmin.flowersoflife.data_provider.di

import com.kuzmin.flowersoflife.data_provider.repository.AuthRepositoryImpl
import com.kuzmin.flowersoflife.feature.auth.api.AuthRepository
import org.koin.dsl.module

val remoteDataProviderModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            get(),
            get()
        )
    }
}