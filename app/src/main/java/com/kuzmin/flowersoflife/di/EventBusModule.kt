package com.kuzmin.flowersoflife.di
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMapImpl
import com.kuzmin.flowersoflife.core.navigation.routing.SharedMapKey.AUTHORIZED
import com.kuzmin.flowersoflife.core.navigation.routing.SharedMapKey.TOPBAR_STATE
import com.kuzmin.flowersoflife.core.ui.ui_model.TopbarState
import org.koin.core.qualifier.named
import org.koin.dsl.module

val eventBusModule = module {
    single<SharedFlowMap<String, User>>(named(AUTHORIZED)) {
        SharedFlowMapImpl<User>()
    }

    single<SharedFlowMap<String, TopbarState>>(named(TOPBAR_STATE)) {
        SharedFlowMapImpl<TopbarState>()
    }
}