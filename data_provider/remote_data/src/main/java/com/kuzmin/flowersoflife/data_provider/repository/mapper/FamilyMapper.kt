package com.kuzmin.flowersoflife.data_provider.repository.mapper

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.domain.model.family_members.ChildDetails
import com.kuzmin.flowersoflife.core.domain.model.financial.FinancialRecord
import com.kuzmin.flowersoflife.core.domain.model.financial.RecordType
import com.kuzmin.flowersoflife.core.domain.model.goals.Goal
import com.kuzmin.flowersoflife.core.domain.model.goals.GoalStatus
import com.kuzmin.flowersoflife.core.domain.model.tasks.Task
import com.kuzmin.flowersoflife.core.domain.model.tasks.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.tasks.TaskType
import com.kuzmin.flowersoflife.core.model.ChildFb
import com.kuzmin.flowersoflife.core.model.ChildDetailsFb
import com.kuzmin.flowersoflife.core.model.FinancialRecordFb
import com.kuzmin.flowersoflife.core.model.GoalFb
import com.kuzmin.flowersoflife.core.model.TaskFb
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object FamilyMapper {
    fun toDomain(childFb: ChildFb): Child {
        return Child(
            childId = childFb.uid.orEmpty(),
            balance = childFb.balance ?: 0,
            name = childFb.firstName.orEmpty()
        )
    }

    fun toDomain(taskFb: TaskFb): Task = Task(
        taskId = taskFb.taskId.orEmpty(),
        startDate = parseDate(taskFb.startDate),
        endDate = parseDate(taskFb.endDate),
        description = taskFb.description.orEmpty(),
        type = enumValueOrDefault(taskFb.type, TaskType.SINGLE),
        status = enumValueOrDefault(taskFb.status, TaskStatus.ACTIVE),
        createdAt = parseDate(taskFb.createdAt),
        reward = taskFb.reward ?: 0,
        fine = taskFb.fine ?: 0,
        parentId = taskFb.parentId.orEmpty(),
        childId = taskFb.childId.orEmpty()
    )

    fun toDomain(goalFb: GoalFb): Goal = Goal(
        goalId = goalFb.goalId.orEmpty(),
        price = goalFb.price ?: 0,
        name = goalFb.name.orEmpty(),
        status = GoalStatus.IN_PROGRESS,
        childId = goalFb.childId.orEmpty()
    )

    fun toDomain(financialRecordFb: FinancialRecordFb): FinancialRecord = FinancialRecord(
        recordId = financialRecordFb.recordId.orEmpty(),
        type = enumValueOrDefault(financialRecordFb.type, RecordType.DEPOSIT),
        amount = financialRecordFb.amount ?: 0,
        rate = financialRecordFb.rate ?: 0,
        description = financialRecordFb.description.orEmpty(),
        startDate = parseDate(financialRecordFb.startDate),
        endDate = parseDate(financialRecordFb.endDate),
        createdAt = parseDate(financialRecordFb.createdAt),
        childId = financialRecordFb.childId.orEmpty()
    )

    fun toDomain(detailsFb: ChildDetailsFb): ChildDetails = ChildDetails(
        child = toDomain(detailsFb.child),
        tasks = detailsFb.tasks.map(::toDomain),
        goals = detailsFb.goals.map(::toDomain),
        financialRecords = detailsFb.financialRecords.map(::toDomain)
    )

    private fun parseDate(value: String?): LocalDateTime =
        value?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME) }
            ?: LocalDateTime.MIN

    private inline fun <reified T : Enum<T>> enumValueOrDefault(value: String?, default: T): T =
        try { if (value == null) default else enumValueOf(value) } catch (_: Exception) { default }
}


