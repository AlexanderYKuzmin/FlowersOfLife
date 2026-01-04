package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFromRemoteDbUseCase

class GetUserFromRemoteDbUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserFromRemoteDbUseCase {
    override suspend fun invoke(userId: String): User? {
        return userRepository.getUserById(userId)
    }
}