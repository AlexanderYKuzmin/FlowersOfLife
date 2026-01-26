package com.kuzmin.flowersoflife.data_provider.di

import com.kuzmin.flowersoflife.core.api.ApiService
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.data_provider.repository.AggregateRepositoryImpl
import com.kuzmin.flowersoflife.data_provider.repository.AuthRepositoryImpl
import com.kuzmin.flowersoflife.data_provider.repository.FamilyRepositoryImpl
import com.kuzmin.flowersoflife.data_provider.repository.UserRepositoryImpl
import com.kuzmin.flowersoflife.feature.api.repository.AuthRepository
import com.kuzmin.flowersoflife.feature.api.repository.FamilyRepository
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
    single<FamilyRepository> {
        FamilyRepositoryImpl(get<ApiService>(), get<PrefManager>())
    }
}