package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.feature.auth.api.usecases.SaveUserDatastoreUseCase
import javax.inject.Inject

class SaveUserDatastoreUseCaseImpl @Inject constructor(
    private val prefManager: PrefManager
) : SaveUserDatastoreUseCase {
    override suspend operator fun invoke(user: User) {
        prefManager.saveUser(user)
    }
}