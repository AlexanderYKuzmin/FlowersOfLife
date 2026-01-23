package com.kuzmin.flowersoflife.feature.home.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.feature.api.repository.FamilyRepository
import com.kuzmin.flowersoflife.feature.api.usecases.home.SaveNewChildUseCase

class SaveNewChildUseCaseImpl(
    private val familyRepository: FamilyRepository
) : SaveNewChildUseCase {
    override suspend fun invoke(child: Child) {
        familyRepository.saveChild(child)
    }
}