package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.core.di.networkModule
import com.kuzmin.flowersoflife.core.local.di.resProviderModule
import com.kuzmin.flowersoflife.core.ui.di.uiEventModule
import com.kuzmin.flowersoflife.data_provider.di.localDataProviderModule
import com.kuzmin.flowersoflife.data_provider.di.remoteDataProviderModule
import com.kuzmin.flowersoflife.feature.auth.di.authModule
import com.kuzmin.flowersoflife.feature.home.di.homeModule
import org.koin.dsl.module
val appModule = module {
    includes(
        appFirebaseModule,
        navigationModule,
        eventBusModule,
        localDataProviderModule,
        mainScreenModule,
        remoteDataProviderModule,
        resProviderModule,
        authModule,
        homeModule,
        uiEventModule,
        networkModule
    )
}