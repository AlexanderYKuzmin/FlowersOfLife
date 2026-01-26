package com.kuzmin.flowersoflife.feature.home.domain.usecases

import com.kuzmin.flowersoflife.feature.api.repository.FamilyRepository
import com.kuzmin.flowersoflife.feature.api.usecases.home.DeleteChildRemoteUseCase

class DeleteChildRemoteUseCaseImpl(
    private val familyRepository: FamilyRepository
) : DeleteChildRemoteUseCase {
    override suspend operator fun invoke(childId: String) {
        familyRepository.deleteChild(childId)
    }
}