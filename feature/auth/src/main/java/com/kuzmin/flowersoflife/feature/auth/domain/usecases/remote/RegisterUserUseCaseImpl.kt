package com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote

import com.kuzmin.flowersoflife.core.domain.mapper.toAuthCredentials
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.feature.api.repository.AuthRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.RegisterUserUseCase

class RegisterUserUseCaseImpl(
    private val authRepository: AuthRepository
) : RegisterUserUseCase {

    override suspend operator fun invoke(user: User): String? {
        return authRepository.registerWithEmail(user.toAuthCredentials())
    }
}