package com.kuzmin.flowersoflife.core.ui.di

import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarController
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SnackbarModule {

    @Binds
    fun bindSnackbarController(controller: SnackbarControllerImpl): SnackbarController
}