package com.kuzmin.flowersoflife.feature.home.domain.usecases

import com.kuzmin.flowersoflife.core.domain.repository.FamilyRepository
import com.kuzmin.flowersoflife.core.domain.usecases.children.GetChildDetailsUseCase
import javax.inject.Inject

class GetChildDetailsUseCaseImpl @Inject constructor(
    private val repository: FamilyRepository
) : GetChildDetailsUseCase {
    override suspend operator fun invoke(childId: String) =
        repository.getChildDetails(childId)
}