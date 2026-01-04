package com.kuzmin.flowersoflife.feature.api.repository.aggregate

import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard

interface UserAggregateRepository {
    suspend fun getUserDashboard(): Result<ChildDashboard>
}