package com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.SaveUserFamilyRemoteUseCase

class SaveUserFamilyRemoteUseCaseImpl(
    private val userRepository: UserRepository
) : SaveUserFamilyRemoteUseCase {
    override suspend operator fun invoke(userFamily: UserFamily): User? {
        return userRepository.saveUserFamily(userFamily)
    }
}