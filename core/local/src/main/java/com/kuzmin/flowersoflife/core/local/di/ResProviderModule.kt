package com.kuzmin.flowersoflife.core.local.di

import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ResProviderModule {

    @Binds
    fun bindResourceProvider(impl: ResourceProviderImpl): ResourceProvider
}