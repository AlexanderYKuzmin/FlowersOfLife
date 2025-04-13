package com.kuzmin.flowersoflife.data_provider.di

import com.kuzmin.flowersoflife.feature.auth.api.PrefManager
import com.kuzmin.flowersoflife.data_provider.local_repo.PrefManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataProviderModule {

    @Binds
    fun bindPrefManager(prefManager: PrefManagerImpl): com.kuzmin.flowersoflife.feature.auth.api.PrefManager
}