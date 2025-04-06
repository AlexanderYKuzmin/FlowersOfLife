package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.usecases.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.data_provider.api.PrefManager
import javax.inject.Inject

class GetUserFromLocalStorageUseCaseImpl @Inject constructor(
    private val prefManager: PrefManager
) : GetUserFromLocalStorageUseCase {
    override suspend fun invoke(): User = prefManager.getUser()
}