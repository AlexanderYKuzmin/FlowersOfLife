package com.kuzmin.flowersoflife.core.model

data class ChildDetailsFb(
    val child: ChildFb,
    val tasks: List<TaskFb>,
    val goals: List<GoalFb>,
    val financialRecords: List<FinancialRecordFb>
)


