package com.kuzmin.flowersoflife.feature.home.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.feature.api.repository.aggregate.AggregateRepository
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildrenDashboardUseCase

class GetChildrenDashboardUseCaseImpl(
    private val aggregateRepository: AggregateRepository
) : GetChildrenDashboardUseCase {
    override suspend operator fun invoke(familyId: String): List<ChildDashboard> {
        return aggregateRepository.getChildrenDashboard(familyId)
    }
}