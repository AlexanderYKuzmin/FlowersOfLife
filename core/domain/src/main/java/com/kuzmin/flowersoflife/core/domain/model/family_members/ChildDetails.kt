package com.kuzmin.flowersoflife.core.domain.model.family_members

import com.kuzmin.flowersoflife.core.domain.model.financial.FinancialRecord
import com.kuzmin.flowersoflife.core.domain.model.goals.Goal
import com.kuzmin.flowersoflife.core.domain.model.tasks.Task

data class ChildDetails(
    val child: Child,

    val tasks: List<Task>,

    val goals: List<Goal>,

    val financialRecords: List<FinancialRecord>
)
