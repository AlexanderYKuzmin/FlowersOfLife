package com.kuzmin.flowersoflife.data_provider.repository

import com.kuzmin.flowersoflife.core.api.ApiService
import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.data_provider.mapper.toChildDto
import com.kuzmin.flowersoflife.feature.api.repository.FamilyRepository

class FamilyRepositoryImpl(
    private val apiService: ApiService,
    private val prefManager: PrefManager
) : FamilyRepository {
    override suspend fun saveChild(child: Child): String? {
        val result = apiService.createChild(
            child.toChildDto(
                prefManager.getFamily()
            )
        )

        return result.body()?.userId
    }

    override suspend fun updateChild(child: Child) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChild(childId: String) {
        apiService.deleteChild(childId)
    }
}