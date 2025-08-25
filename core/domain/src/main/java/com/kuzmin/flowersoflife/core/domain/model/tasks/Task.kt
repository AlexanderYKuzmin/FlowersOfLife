package com.kuzmin.flowersoflife.core.domain.model.tasks

import java.time.LocalDateTime

data class Task(
    val taskId: String,

    val startDate: LocalDateTime,

    val endDate: LocalDateTime,

    val description: String,

    val type: TaskType,

    val status: TaskStatus,

    val createdAt: LocalDateTime,

    val reward: Int,

    val fine: Int,

    val parentId: String,

    val childId: String
)
