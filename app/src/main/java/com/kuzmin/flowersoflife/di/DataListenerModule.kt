package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.common.constants.SharedMapKey.AUTHORIZED
import com.kuzmin.flowersoflife.common.constants.SharedMapKey.TOPBAR_STATE
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.local.data_listeners.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.data_listeners.SharedFlowMapImpl
import com.kuzmin.flowersoflife.core.ui.ui_model.TopbarState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
interface DataListenerModule {

    @Binds
    @Named(AUTHORIZED)
    fun bindUserSharedFlowMap(impl: SharedFlowMapImpl<String, User>): SharedFlowMap<String, User>

    @Binds
    @Named(TOPBAR_STATE)
    fun bindTopbarSharedFlowMap(impl: SharedFlowMapImpl<String, TopbarState>): SharedFlowMap<String, TopbarState>
}
