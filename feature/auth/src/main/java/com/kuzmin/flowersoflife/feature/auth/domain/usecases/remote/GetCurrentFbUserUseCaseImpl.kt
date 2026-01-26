package com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote

import com.google.firebase.auth.FirebaseUser
import com.kuzmin.flowersoflife.feature.api.repository.AuthRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetCurrentFbUserUseCase

class GetCurrentFbUserUseCaseImpl(
    private val authRepository: AuthRepository
) : GetCurrentFbUserUseCase {
    override suspend fun invoke(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }
}