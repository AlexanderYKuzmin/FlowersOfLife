package com.kuzmin.flowersoflife.feature.home.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.Family
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetFamilyFromLocalUseCase

class GetFamilyFromLocalUseCaseImpl(
    private val prefManager: PrefManager
) : GetFamilyFromLocalUseCase {
    override suspend operator fun invoke(): Family {
        return prefManager.getFamily()
    }
}