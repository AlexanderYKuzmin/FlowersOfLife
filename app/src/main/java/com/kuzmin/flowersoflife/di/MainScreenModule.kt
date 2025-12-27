package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.ui.viewmodels.MainScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainScreenModule = module {
    viewModel { MainScreenViewModel(
        get(),
        get(),
        get(), get(),
        get())
    }
}