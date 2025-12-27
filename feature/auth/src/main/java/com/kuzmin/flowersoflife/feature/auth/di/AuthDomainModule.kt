package com.kuzmin.flowersoflife.feature.auth.di

import com.kuzmin.flowersoflife.core.domain.usecases.auth.CheckAuthUseCase
import com.kuzmin.flowersoflife.core.domain.usecases.auth.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.feature.auth.api.usecases.RegisterUserUseCase
import com.kuzmin.flowersoflife.feature.auth.api.usecases.SaveUserDatastoreUseCase
import com.kuzmin.flowersoflife.feature.auth.api.usecases.SignInUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.CheckAuthUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.GetUserFromLocalStorageUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.RegisterUserUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.SaveUserDatastoreUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.SignInUseCaseImpl
import org.koin.dsl.module

val authDomainModule = module {
    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get()) }
    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<CheckAuthUseCase> { CheckAuthUseCaseImpl(get()) }
    single<GetUserFromLocalStorageUseCase> { GetUserFromLocalStorageUseCaseImpl(get()) }
    single<SaveUserDatastoreUseCase> { SaveUserDatastoreUseCaseImpl(get()) }
}