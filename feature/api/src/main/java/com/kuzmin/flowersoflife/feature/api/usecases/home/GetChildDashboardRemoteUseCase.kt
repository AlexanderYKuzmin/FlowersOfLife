package com.kuzmin.flowersoflife.feature.api.usecases.home

import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard

interface GetChildDashboardRemoteUseCase {
    suspend operator fun invoke(childId: String): ChildDashboard?
}