package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.core.domain.usecases.auth.GetUserFromLocalStorageUseCase
import javax.inject.Inject

class GetUserFromLocalStorageUseCaseImpl @Inject constructor(
    private val prefManager: PrefManager
) : GetUserFromLocalStorageUseCase {
    override suspend fun invoke(): User = prefManager.getUser()
}