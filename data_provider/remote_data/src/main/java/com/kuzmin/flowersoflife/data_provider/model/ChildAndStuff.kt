package com.kuzmin.flowersoflife.data_provider.model

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.domain.model.financial.FinancialRecord
import com.kuzmin.flowersoflife.core.domain.model.goals.Goal
import com.kuzmin.flowersoflife.core.domain.model.tasks.Task

data class ChildAndStuff(
    val child: Child,

    val tasks: List<Task>,

    val goals: List<Goal>,

    val financialRecords: List<FinancialRecord>
)
