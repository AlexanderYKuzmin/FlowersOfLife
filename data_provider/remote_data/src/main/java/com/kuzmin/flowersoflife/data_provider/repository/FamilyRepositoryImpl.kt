package com.kuzmin.flowersoflife.data_provider.repository

import com.kuzmin.flowersoflife.core.api.ApiService
import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.feature.api.repository.FamilyRepository

class FamilyRepositoryImpl(
    private val apiService: ApiService
) : FamilyRepository {
    override suspend fun saveChild(child: Child) {

    }

    override suspend fun updateChild(child: Child) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChild(childId: String) {
        TODO("Not yet implemented")
    }
}