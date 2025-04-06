package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.usecases.SignInUseCase
import com.kuzmin.flowersoflife.data_provider.api.AuthRepository
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SignInUseCase {

    override suspend fun invoke(user: User) = authRepository.signInWithEmail(user)
}