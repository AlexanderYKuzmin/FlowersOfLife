package com.kuzmin.flowersoflife.feature.api.repository.aggregate

import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard

interface AggregateRepository {
    suspend fun getChildrenDashboard(familyId: String): List<ChildDashboard>
}