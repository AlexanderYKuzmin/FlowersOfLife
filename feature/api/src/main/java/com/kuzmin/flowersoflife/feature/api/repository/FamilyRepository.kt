package com.kuzmin.flowersoflife.feature.api.repository

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child

interface FamilyRepository {
    suspend fun saveChild(child: Child)

    suspend fun updateChild(child: Child)

    suspend fun deleteChild(childId: String)
}