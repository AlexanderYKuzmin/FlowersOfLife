package com.kuzmin.flowersoflife.data_provider.repository.api

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.domain.model.financial.FinancialRecord
import com.kuzmin.flowersoflife.core.domain.model.goals.Goal
import com.kuzmin.flowersoflife.core.domain.model.tasks.Task

interface BasicRepository {

    suspend fun getAllChildrenByParentId(parentId: String): List<Child>

    suspend fun getAllTaskByChildId(childId: String): List<Task>

    suspend fun getAllGoalsByChildId(childId: String): List<Goal>

    suspend fun getAllFinancialRecordsByChildId(childId: String): List<FinancialRecord>
}