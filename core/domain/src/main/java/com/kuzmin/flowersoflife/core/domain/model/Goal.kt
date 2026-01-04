package com.kuzmin.flowersoflife.core.domain.model

data class Goal(
    val goalId: String,

    val price: Int,

    val name: String,

    val status: GoalStatus,

    val childId: String
)

enum class GoalStatus(val value: String) {
    CREATED("created"),
    COMPLETED("completed"),
    ASSIGNED("assigned");

    companion object {
        fun fromValue(value: String): GoalStatus? {
            return entries.find { it.value == value }
        }
    }
}
