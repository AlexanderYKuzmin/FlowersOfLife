package com.kuzmin.flowersoflife.core.domain.model.financial

import java.time.LocalDateTime

data class FinancialRecord(
    val recordId: String,

    val type: RecordType,

    val amount: Int,

    val rate: Int,

    val description: String,

    val startDate: LocalDateTime,

    val endDate: LocalDateTime,

    val createdAt: LocalDateTime,

    val childId: String
)