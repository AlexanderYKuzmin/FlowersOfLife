package com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetUserFromRemoteUseCase

class GetUserFromRemoteUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserFromRemoteUseCase {
    override suspend fun invoke(userId: String): User? {
        return userRepository.getUserById(userId)
    }
}