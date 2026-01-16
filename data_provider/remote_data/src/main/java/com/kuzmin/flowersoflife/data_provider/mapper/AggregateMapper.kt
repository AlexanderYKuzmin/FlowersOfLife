package com.kuzmin.flowersoflife.data_provider.mapper

import com.kuzmin.flowersoflife.core.domain.model.FinanceRecordType
import com.kuzmin.flowersoflife.core.domain.model.FinancialRecord
import com.kuzmin.flowersoflife.core.domain.model.Goal
import com.kuzmin.flowersoflife.core.domain.model.GoalStatus
import com.kuzmin.flowersoflife.core.domain.model.Task
import com.kuzmin.flowersoflife.core.domain.model.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.Wallet
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.model.dto.FinancialRecordDto
import com.kuzmin.flowersoflife.core.model.dto.GoalDto
import com.kuzmin.flowersoflife.core.model.dto.TaskDto
import com.kuzmin.flowersoflife.core.model.dto.WalletDto
import com.kuzmin.flowersoflife.core.model.dto.aggregate.ChildDashboardDto
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun ChildDashboardDto.toChildDashboard(): ChildDashboard {
    return ChildDashboard(
         user = user.toUserModel(),
         wallet = wallet.toWallet(),
         tasks = tasks.map { it.toTask() },
         goals = goals.map { it.toGoal() },
         financialRecords = financialRecords.map { it.toFinancialRecord() }
    )
}

fun WalletDto.toWallet(): Wallet {
    return Wallet(
        walletId = walletId,
        balance = balance,
        userId = userId
    )
}

fun TaskDto.toTask(): Task {
    return Task(
        taskId = taskId,
        description = description,
        status = TaskStatus.valueOf(status),
        startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateStart), ZoneId.systemDefault()),
        endDate = dateEnd?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
        } ?: LocalDateTime.now(),
        reward = reward,
        fine = fine,
        parentId = parentId,
        childId = childId
    )
}

fun GoalDto.toGoal(): Goal {
    return Goal(
        goalId = goalId,
        name = name,
        status = GoalStatus.fromValue(status) ?: GoalStatus.CREATED,
        price = price,
        childId = childId
    )
}

fun FinancialRecordDto.toFinancialRecord(): FinancialRecord {
    return FinancialRecord(
        opId = opId,
        amount = amount.toInt(),
        type = FinanceRecordType.fromValue(type),
        rate = rate.toInt(),
        description = description ?: "",
        startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateStart), ZoneId.systemDefault()),
        endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateEnd), ZoneId.systemDefault()),
        childId = childId
    )
}