package com.kuzmin.flowersoflife.core.local.di

import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProviderImpl
import org.koin.dsl.module

val resProviderModule = module {
    single<ResourceProvider> { ResourceProviderImpl(get()) }
}