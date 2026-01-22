package com.kuzmin.flowersoflife.data_provider.di

import com.kuzmin.flowersoflife.data_provider.repository.AggregateRepositoryImpl
import com.kuzmin.flowersoflife.data_provider.repository.AuthRepositoryImpl
import com.kuzmin.flowersoflife.data_provider.repository.UserRepositoryImpl
import com.kuzmin.flowersoflife.feature.api.repository.AuthRepository
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository
import com.kuzmin.flowersoflife.feature.api.repository.aggregate.AggregateRepository
import org.koin.dsl.module

val remoteDataProviderModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    single<AggregateRepository> {
        AggregateRepositoryImpl(get())
    }
}