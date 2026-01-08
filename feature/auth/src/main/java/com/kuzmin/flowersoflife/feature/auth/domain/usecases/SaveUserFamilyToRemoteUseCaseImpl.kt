package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.SaveUserFamilyToRemoteUseCase

class SaveUserFamilyToRemoteUseCaseImpl(
    private val userRepository: UserRepository
) : SaveUserFamilyToRemoteUseCase {
    override suspend operator fun invoke(userFamily: UserFamily): User? {
        return userRepository.saveUserFamily(userFamily)
    }
}