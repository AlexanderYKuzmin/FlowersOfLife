package com.kuzmin.flowersoflife.feature.auth.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFamilyFromLocalUseCase

class GetUserFamilyFromLocalUseCaseImpl(
    private val prefManager: PrefManager
) : GetUserFamilyFromLocalUseCase {
    override suspend fun invoke(): UserFamily {
        val user = prefManager.getUser()
        val family = prefManager.getFamily()

        return UserFamily(user, family)
    }
}