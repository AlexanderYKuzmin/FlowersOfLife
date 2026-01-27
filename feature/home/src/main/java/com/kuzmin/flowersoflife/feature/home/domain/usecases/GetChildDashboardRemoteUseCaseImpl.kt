package com.kuzmin.flowersoflife.feature.home.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.feature.api.repository.aggregate.AggregateRepository
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildDashboardRemoteUseCase

class GetChildDashboardRemoteUseCaseImpl(
    private val aggregateRepository: AggregateRepository
) : GetChildDashboardRemoteUseCase {
    override suspend fun invoke(childId: String): ChildDashboard? {
        return aggregateRepository.getChildDashboard(childId)
    }
}