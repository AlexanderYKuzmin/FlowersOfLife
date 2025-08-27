package com.kuzmin.flowersoflife.feature.home.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.domain.repository.FamilyRepository
import com.kuzmin.flowersoflife.core.domain.usecases.children.GetChildrenListUseCase
import javax.inject.Inject

class GetChildrenListUseCaseImpl @Inject constructor(
    private val repository: FamilyRepository,
) : GetChildrenListUseCase {
    override suspend operator fun invoke(parentId: String): List<Child> =
        repository.getChildrenList(parentId)
}