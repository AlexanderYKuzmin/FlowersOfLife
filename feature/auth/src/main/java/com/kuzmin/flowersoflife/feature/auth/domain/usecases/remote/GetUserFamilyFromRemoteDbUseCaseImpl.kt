package com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote

import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetUserFamilyFromRemoteUseCase

class GetUserFamilyFromRemoteDbUseCaseImpl(
    private val userRepository: UserRepository
) : GetUserFamilyFromRemoteUseCase {
    override suspend operator fun invoke(userId: String): UserFamily? {
        return userRepository.getUserFamilyById(userId)
    }
}