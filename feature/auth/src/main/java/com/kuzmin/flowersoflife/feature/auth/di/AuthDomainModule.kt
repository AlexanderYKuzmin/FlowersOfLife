package com.kuzmin.flowersoflife.feature.auth.di

import com.kuzmin.flowersoflife.core.domain.usecases.CheckAuthUseCase
import com.kuzmin.flowersoflife.core.domain.usecases.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.core.domain.usecases.RegisterUserUseCase
import com.kuzmin.flowersoflife.core.domain.usecases.SignInUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.CheckAuthUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.GetUserFromLocalStorageUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.RegisterUserUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.SignInUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthDomainModule {

    @Binds
    fun bindRegisterUserUseCase(registerUserUseCase: RegisterUserUseCaseImpl): RegisterUserUseCase

    @Binds
    fun bindSignInUserUseCase(signInUserUseCase: SignInUseCaseImpl): SignInUseCase

    @Binds
    fun bindCheckAuthUseCase(checkAuthUseCase: CheckAuthUseCaseImpl): CheckAuthUseCase

    @Binds
    fun bindGetUserFromLocalStorageUseCase(
        getUserFromLocalStorageUseCase: GetUserFromLocalStorageUseCaseImpl
    ): GetUserFromLocalStorageUseCase

}