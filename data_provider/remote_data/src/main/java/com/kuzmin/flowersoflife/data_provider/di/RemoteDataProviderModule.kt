package com.kuzmin.flowersoflife.data_provider.di

import com.kuzmin.flowersoflife.data_provider.repository.AuthRepositoryImpl
import com.kuzmin.flowersoflife.data_provider.repository.UserRepositoryImpl
import com.kuzmin.flowersoflife.feature.api.repository.AuthRepository
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository
import org.koin.dsl.module

val remoteDataProviderModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}