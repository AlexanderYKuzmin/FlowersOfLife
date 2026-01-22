package com.kuzmin.flowersoflife.feature.api.usecases.home

import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard

interface GetChildrenDashboardUseCase {
    suspend operator fun invoke(familyId: String): List<ChildDashboard>
}