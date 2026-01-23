package com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.SaveUserRemoteUseCase

class SaveUserRemoteUseCaseImpl(
    private val userRepository: UserRepository
) : SaveUserRemoteUseCase {
    override suspend fun invoke(userFamily: UserFamily): User? {
        TODO("Not yet implemented")
    }
}