package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFromLocalUseCase

class GetUserFromLocalUseCaseImpl(
    private val prefManager: PrefManager
) : GetUserFromLocalUseCase {
    override suspend fun invoke(): User = prefManager.getUser()
}