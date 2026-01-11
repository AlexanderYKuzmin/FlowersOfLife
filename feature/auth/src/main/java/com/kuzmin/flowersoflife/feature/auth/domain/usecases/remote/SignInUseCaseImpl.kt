package com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote

import com.kuzmin.flowersoflife.core.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.feature.api.repository.AuthRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.SignInUseCase

class SignInUseCaseImpl(
    private val authRepository: AuthRepository
) : SignInUseCase {

    override suspend fun invoke(credentials: AuthCredentials) = authRepository.signInWithEmail(credentials)
}