package com.kuzmin.flowersoflife.core

import com.kuzmin.flowersoflife.core.model.ChildDetailsFb
import com.kuzmin.flowersoflife.core.model.ChildFb

interface FamilyService {

    suspend fun getChildrenList(groupName: String): List<ChildFb>

    suspend fun getChildrenDetailsList(groupName: String): List<ChildDetailsFb>

    suspend fun getChild(childId: String): ChildFb

    suspend fun getChildDetails(childId: String): ChildDetailsFb
}