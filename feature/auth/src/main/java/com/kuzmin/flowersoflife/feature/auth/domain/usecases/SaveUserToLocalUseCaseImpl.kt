package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.feature.api.usecases.user.SaveUserToLocalUseCase

class SaveUserToLocalUseCaseImpl(
    private val prefManager: PrefManager
) : SaveUserToLocalUseCase {
    override suspend operator fun invoke(user: User) {
        prefManager.saveUser(user)
    }
}