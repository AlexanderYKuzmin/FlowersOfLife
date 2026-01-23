package com.kuzmin.flowersoflife.feature.home.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.feature.api.repository.FamilyRepository
import com.kuzmin.flowersoflife.feature.api.usecases.home.UpdateChildRemoteUseCase

class UpdateChildRemoteUseCaseImpl(
    private val familyRepository: FamilyRepository
) : UpdateChildRemoteUseCase {
    override suspend fun invoke(child: Child) {
        TODO()
    }
}