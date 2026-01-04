package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.feature.api.repository.AuthRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFromFbUseCase

class GetUserFromFbUseCaseImpl(
    private val authRepository: AuthRepository
) : GetUserFromFbUseCase {
    override suspend fun invoke(): User? {
        val firebaseUser = authRepository.getCurrentUser()

        firebaseUser?.let {
            return User(
                userId = it.uid,
                email = it.email ?: return null,
                familyId = ""
            )
        }
        return null
    }
}