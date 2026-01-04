package com.kuzmin.flowersoflife.core.ui.di

import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarController
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarControllerImpl
import org.koin.dsl.module

val uiEventModule = module {
    single<SnackbarController> { SnackbarControllerImpl(get()) }
}