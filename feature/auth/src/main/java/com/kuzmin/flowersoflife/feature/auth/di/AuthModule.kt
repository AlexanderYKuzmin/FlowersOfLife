package com.kuzmin.flowersoflife.feature.auth.di

import com.kuzmin.flowersoflife.core.domain.usecases.auth.CheckAuthUseCase
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.UI_EVENT
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFromFbUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFromLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFromRemoteDbUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.RegisterUserUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.SaveUserToLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.SignInUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.CheckAuthUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.GetUserFamilyFromLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.GetUserFromFbUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.GetUserFromLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.GetUserFromRemoteDbUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.RegisterUserUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.SaveUserToLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.SignInUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.ui.viewmodels.AuthLoginViewModel
import com.kuzmin.flowersoflife.feature.auth.ui.viewmodels.AuthRegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule = module {
    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get()) }
    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<CheckAuthUseCase> { CheckAuthUseCaseImpl(get()) }
    single<GetUserFromFbUseCase> { GetUserFromFbUseCaseImpl(get()) }
    single<GetUserFromLocalUseCase> { GetUserFromLocalUseCaseImpl(get()) }
    single<GetUserFamilyFromLocalUseCase> { GetUserFamilyFromLocalUseCaseImpl(get()) }
    single<SaveUserToLocalUseCase> { SaveUserToLocalUseCaseImpl(get()) }
    single<GetUserFromRemoteDbUseCase> { GetUserFromRemoteDbUseCaseImpl(get()) }

    viewModel {
        AuthLoginViewModel(
            signInUseCase = get(),
            getUserFromLocalUseCase = get(),
            getUserFamilyFromLocalUseCase = get(),
            navigationManager = get(),
            resourceProvider = get(),
            sharedFlowMap = get(named(UI_EVENT)),
        )
    }

    viewModel {
        AuthRegisterViewModel(
            registerUserUseCase = get(),
            saveUserFamilyToLocalUseCase = get(),
            resourceProvider = get(),
            navigationManager = get(),
            sharedFlowMap = get(named(UI_EVENT)),
        )
    }
}