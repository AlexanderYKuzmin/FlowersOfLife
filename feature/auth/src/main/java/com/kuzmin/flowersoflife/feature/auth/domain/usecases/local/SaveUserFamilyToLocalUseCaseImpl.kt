package com.kuzmin.flowersoflife.feature.auth.domain.usecases.local

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.SaveUserFamilyToLocalUseCase

class SaveUserFamilyToLocalUseCaseImpl(
    private val prefManager: PrefManager
) : SaveUserFamilyToLocalUseCase {
    override suspend operator fun invoke(userFamily: UserFamily): User? {
        with(userFamily) {
            prefManager.saveUser(user)
            prefManager.saveFamily(family)
        }
        return userFamily.user
    }
}