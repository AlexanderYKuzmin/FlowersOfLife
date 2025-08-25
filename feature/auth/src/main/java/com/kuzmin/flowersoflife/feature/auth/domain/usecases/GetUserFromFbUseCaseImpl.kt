package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.feature.auth.api.AuthRepository
import com.kuzmin.flowersoflife.feature.auth.api.usecases.GetUserFromFbUseCase
import javax.inject.Inject

class GetUserFromFbUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : GetUserFromFbUseCase {

    override suspend fun invoke(): User = User()
}