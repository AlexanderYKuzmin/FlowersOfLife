package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.usecases.CheckAuthUseCase
import com.kuzmin.flowersoflife.data_provider.api.AuthRepository
import javax.inject.Inject

class CheckAuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : CheckAuthUseCase {

    override suspend fun invoke(): Boolean = authRepository.isUserAuthorized()
}
