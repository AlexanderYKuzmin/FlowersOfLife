package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.core.local.di.resProviderModule
import com.kuzmin.flowersoflife.core.ui.di.uiEventModule
import com.kuzmin.flowersoflife.data_provider.di.localDataProviderModule
import com.kuzmin.flowersoflife.data_provider.di.remoteDataProviderModule
import com.kuzmin.flowersoflife.feature.auth.di.authDomainModule
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
        authDomainModule,
        uiEventModule
    )
}