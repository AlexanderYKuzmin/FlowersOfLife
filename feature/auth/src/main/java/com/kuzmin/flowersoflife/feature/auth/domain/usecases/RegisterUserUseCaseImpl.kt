package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.feature.auth.api.AuthRepository
import com.kuzmin.flowersoflife.feature.auth.api.usecases.RegisterUserUseCase
import javax.inject.Inject

class RegisterUserUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : RegisterUserUseCase {

    override suspend operator fun invoke(user: User) = authRepository.registerWithEmail(user)
}