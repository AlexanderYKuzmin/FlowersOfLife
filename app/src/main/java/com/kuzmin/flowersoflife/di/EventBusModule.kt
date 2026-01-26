package com.kuzmin.flowersoflife.di
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.CHILD_EVENT
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.UI_EVENT
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMapImpl
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.home.domain.event.ChildEvent
import org.koin.core.qualifier.named
import org.koin.dsl.module

val eventBusModule = module {
    single<SharedFlowMap<UiEvent>>(named(UI_EVENT)) {
        SharedFlowMapImpl()
    }
    single<SharedFlowMap<ChildEvent>>(named(CHILD_EVENT)) {
        SharedFlowMapImpl()
    }
}