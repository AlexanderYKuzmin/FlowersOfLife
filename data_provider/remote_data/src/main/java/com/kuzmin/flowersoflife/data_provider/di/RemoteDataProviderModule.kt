package com.kuzmin.flowersoflife.data_provider.di

import com.kuzmin.flowersoflife.core.domain.repository.FamilyRepository
import com.kuzmin.flowersoflife.feature.auth.api.AuthRepository
import com.kuzmin.flowersoflife.data_provider.repository.AuthRepositoryImpl
import com.kuzmin.flowersoflife.data_provider.repository.FamilyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataProviderModule {

    @Binds
    fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindFamilyRepository(familyRepositoryImpl: FamilyRepositoryImpl): FamilyRepository
}