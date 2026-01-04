package com.kuzmin.flowersoflife.core.domain.model

import java.time.LocalDateTime

data class Task(
    val taskId: String,

    val startDate: LocalDateTime,

    val endDate: LocalDateTime,

    val description: String,

    val status: TaskStatus,

    val reward: Int,

    val fine: Int,

    val parentId: String,

    val childId: String
)

enum class TaskStatus(val value: String) {
    CREATED("created"),
    PENDING("pending"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    CANCELED("canceled"),
    FAILED("failed");

    companion object {
        fun fromValue(value: String): TaskStatus? {
            return entries.find { it.value == value }
        }
    }
}

