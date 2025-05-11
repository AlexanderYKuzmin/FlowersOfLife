package com.kuzmin.flowersoflife.core.ui.di

import com.kuzmin.flowersoflife.core.ui.event.UiEventFlow
import com.kuzmin.flowersoflife.core.ui.event.UiEventFlowImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UiEventModule {

    @Binds
    fun bindUiEventFlow(flow: UiEventFlowImpl): UiEventFlow
}