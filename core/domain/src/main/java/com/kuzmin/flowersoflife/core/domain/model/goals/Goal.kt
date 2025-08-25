package com.kuzmin.flowersoflife.core.domain.model.goals

data class Goal(
    val goalId: String,

    val price: Int,

    val name: String,

    val status: GoalStatus,

    val childId: String
)
