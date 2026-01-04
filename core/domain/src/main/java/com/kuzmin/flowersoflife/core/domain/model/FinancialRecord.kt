package com.kuzmin.flowersoflife.core.domain.model

import java.time.LocalDateTime

data class FinancialRecord(
    val opId: String,

    val type: FinanceRecordType,

    val amount: Int,

    val rate: Int,

    val description: String = "",

    val startDate: LocalDateTime,

    val endDate: LocalDateTime,

    val childId: String
)

enum class FinanceRecordType(val value: String) {
    DEPOSIT("deposit"),
    CREDIT("credit");

    companion object {
        fun fromValue(value: String): FinanceRecordType {
            return entries.find { it.value == value } ?: throw IllegalArgumentException("Invalid FinanceRecordType value: $value")
        }
    }
}