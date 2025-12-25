package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.local.data_listeners.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.data_listeners.SharedFlowMapImpl
import com.kuzmin.flowersoflife.core.navigation.routing.SharedMapKey.AUTHORIZED
import com.kuzmin.flowersoflife.core.navigation.routing.SharedMapKey.TOPBAR_STATE
import com.kuzmin.flowersoflife.core.ui.ui_model.TopbarState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
interface EventBusModule {

    @Binds
    @Named(AUTHORIZED)
    fun bindUserSharedFlowMap(impl: SharedFlowMapImpl<User>): SharedFlowMap<String, User>

    @Binds
    @Named(TOPBAR_STATE)
    fun bindTopbarSharedFlowMap(impl: SharedFlowMapImpl<TopbarState>): SharedFlowMap<String, TopbarState>
}
