package com.kuzmin.flowersoflife.core.domain.repository

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.domain.model.family_members.ChildDetails

interface FamilyRepository {

    suspend fun getChildrenList(groupName: String): List<Child>

    suspend fun getChildDetails(childId: String): ChildDetails
}