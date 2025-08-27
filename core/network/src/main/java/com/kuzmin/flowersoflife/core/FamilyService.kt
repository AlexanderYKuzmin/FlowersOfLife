package com.kuzmin.flowersoflife.core

import com.kuzmin.flowersoflife.core.model.ChildFb
import com.kuzmin.flowersoflife.core.model.ChildDetailsFb

interface FamilyService {

    suspend fun getChildrenList(groupName: String): List<ChildFb>

    suspend fun getChild(childId: String): ChildFb

    suspend fun getChildDetails(childId: String): ChildDetailsFb
}