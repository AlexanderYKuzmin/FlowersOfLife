package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.usecases.auth.CheckAuthUseCase
import com.kuzmin.flowersoflife.feature.auth.api.AuthRepository
import javax.inject.Inject

class CheckAuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : CheckAuthUseCase {

    override suspend fun invoke(): Boolean = authRepository.isUserAuthorized()
}
