package com.kuzmin.flowersoflife.core.ui.di

import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarController
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarControllerImpl
import com.kuzmin.flowersoflife.core.ui.event.UiEventFlow
import com.kuzmin.flowersoflife.core.ui.event.UiEventFlowImpl
import org.koin.dsl.module

val uiEventModule = module {
    single<UiEventFlow> { UiEventFlowImpl() }
    single<SnackbarController> { SnackbarControllerImpl(get()) }
}