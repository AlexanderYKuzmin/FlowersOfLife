package com.kuzmin.flowersoflife.data_provider.repository

import com.kuzmin.flowersoflife.core.FamilyService
import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.domain.model.family_members.ChildDetails
import com.kuzmin.flowersoflife.core.domain.repository.FamilyRepository
import com.kuzmin.flowersoflife.data_provider.repository.mapper.FamilyMapper
import javax.inject.Inject

class FamilyRepositoryImpl @Inject constructor(
    private val familyService: FamilyService
) : FamilyRepository{
    override suspend fun getChildrenList(groupName: String): List<Child> {
        val childrenFb = familyService.getChildrenList(groupName)
        return childrenFb.map { childFb -> FamilyMapper.toDomain(childFb) }
    }

    override suspend fun getChildrenDetailsList(groupName: String): List<ChildDetails> {
        val childrenDetailsFb = familyService.getChildrenDetailsList(groupName)
        return childrenDetailsFb.map { detailsFb -> FamilyMapper.toDomain(detailsFb) }
    }

    override suspend fun getChildDetails(childId: String): ChildDetails {
        val detailsFb = familyService.getChildDetails(childId)
        return FamilyMapper.toDomain(detailsFb)
    }
}