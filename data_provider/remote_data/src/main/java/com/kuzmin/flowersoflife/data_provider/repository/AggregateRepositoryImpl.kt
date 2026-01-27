package com.kuzmin.flowersoflife.data_provider.repository

import com.kuzmin.flowersoflife.core.api.ApiService
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.data_provider.mapper.toChildDashboard
import com.kuzmin.flowersoflife.feature.api.repository.aggregate.AggregateRepository

class AggregateRepositoryImpl(
    private val apiService: ApiService
) : AggregateRepository {

    override suspend fun getChildrenDashboard(familyId: String): List<ChildDashboard> {
        val result = apiService.getFamilyDashboard(familyId)
        return result.body()?.map { it.toChildDashboard() } ?: emptyList()
    }

    override suspend fun getChildDashboard(childId: String): ChildDashboard {
        val result = apiService.getChildDashboard(childId)
        return result.body()?.toChildDashboard() ?: throw Exception("Child not found")
    }
}