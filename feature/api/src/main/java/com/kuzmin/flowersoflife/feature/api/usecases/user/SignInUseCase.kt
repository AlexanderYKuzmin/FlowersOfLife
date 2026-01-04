package com.kuzmin.flowersoflife.feature.api.usecases.user

import com.kuzmin.flowersoflife.core.domain.model.AuthCredentials

interface SignInUseCase {
    suspend operator fun invoke(credentials: AuthCredentials): Boolean
}