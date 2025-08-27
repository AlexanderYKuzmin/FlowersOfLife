package com.kuzmin.flowersoflife.core.model

data class TaskFb(
    val taskId: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val description: String? = null,
    val type: String? = null,
    val status: String? = null,
    val createdAt: String? = null,
    val reward: Int? = null,
    val fine: Int? = null,
    val parentId: String? = null,
    val childId: String? = null
)


