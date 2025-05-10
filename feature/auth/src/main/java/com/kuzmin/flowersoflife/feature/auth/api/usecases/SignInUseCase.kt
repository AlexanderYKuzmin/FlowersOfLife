package com.kuzmin.flowersoflife.feature.auth.api.usecases

import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthCredentials

interface SignInUseCase {
    suspend operator fun invoke(credentials: AuthCredentials): Boolean
}