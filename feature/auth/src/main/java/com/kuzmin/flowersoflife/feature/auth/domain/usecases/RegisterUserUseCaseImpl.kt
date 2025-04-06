package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.usecases.RegisterUserUseCase
import com.kuzmin.flowersoflife.data_provider.api.AuthRepository
import javax.inject.Inject

class RegisterUserUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : RegisterUserUseCase {

    override suspend operator fun invoke(user: User) = authRepository.registerWithEmail(user)
}