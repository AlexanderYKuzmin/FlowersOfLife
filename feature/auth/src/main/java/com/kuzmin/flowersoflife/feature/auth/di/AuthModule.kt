package com.kuzmin.flowersoflife.feature.auth.di

import com.kuzmin.flowersoflife.core.domain.usecases.auth.CheckAuthUseCase
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.UI_EVENT
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetUserFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetUserFromLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.SaveUserFamilyToLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.SaveUserToLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetCurrentFbUserUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetUserFamilyFromRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetUserFromFbUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetUserFromRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.RegisterUserUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.SaveUserFamilyRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.SaveUserRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.SignInUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.UpdateUserRemoteUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.local.GetUserFamilyFromLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.local.GetUserFromLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.local.SaveUserFamilyToLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.local.SaveUserToLocalUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.CheckAuthUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.GetCurrentFbUserUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.GetUserFamilyFromRemoteDbUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.GetUserFromFbUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.GetUserFromRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.RegisterUserUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.SaveUserFamilyRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.SaveUserRemoteUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.SignInUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote.UpdateUserRemoteUseCaseImpl
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
    single<GetUserFromRemoteUseCase> { GetUserFromRemoteUseCaseImpl(get()) }
    single<SaveUserFamilyToLocalUseCase> { SaveUserFamilyToLocalUseCaseImpl(get()) }
    single<SaveUserFamilyRemoteUseCase> { SaveUserFamilyRemoteUseCaseImpl(get()) }
    single<GetUserFamilyFromRemoteUseCase> { GetUserFamilyFromRemoteDbUseCaseImpl(get()) }
    single<GetCurrentFbUserUseCase> { GetCurrentFbUserUseCaseImpl(get()) }
    single<GetUserFromRemoteUseCase> { GetUserFromRemoteUseCaseImpl(get()) }
    single<SaveUserRemoteUseCase> { SaveUserRemoteUseCaseImpl(get()) }
    single<UpdateUserRemoteUseCase> { UpdateUserRemoteUseCaseImpl(get()) }


    viewModel {
        AuthLoginViewModel(
            signInUseCase = get(),
            getUserFamilyFromLocalUseCase = get(),
            getUserFamilyFromRemoteUseCase = get(),
            getCurrentFbUserUseCase = get(),
            saveUserFamilyToLocalUseCase = get(),
            navigationManager = get(),
            resourceProvider = get(),
            sharedFlowMap = get(named(UI_EVENT)),
        )
    }

    viewModel {
        AuthRegisterViewModel(
            registerUserUseCase = get(),
            saveUserFamilyToLocalUseCase = get(),
            saveUserFamilyRemoteUseCase = get(),
            resourceProvider = get(),
            navigationManager = get(),
            sharedFlowMap = get(named(UI_EVENT)),
        )
    }
}