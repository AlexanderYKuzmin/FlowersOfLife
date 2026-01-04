package com.kuzmin.flowersoflife.core.domain.model.aggregate

import com.kuzmin.flowersoflife.core.domain.model.FinancialRecord
import com.kuzmin.flowersoflife.core.domain.model.Goal
import com.kuzmin.flowersoflife.core.domain.model.Task
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.Wallet

data class ChildDashboard(
    val user: User,

    val wallet: Wallet,

    val tasks: List<Task>,

    val goals: List<Goal>,

    val financialRecords: List<FinancialRecord>,
)
