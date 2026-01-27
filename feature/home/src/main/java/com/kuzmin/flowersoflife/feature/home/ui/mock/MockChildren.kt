package com.kuzmin.flowersoflife.feature.home.ui.mock

import com.kuzmin.flowersoflife.core.domain.model.FinanceRecordType
import com.kuzmin.flowersoflife.core.domain.model.FinancialRecord
import com.kuzmin.flowersoflife.core.domain.model.Goal
import com.kuzmin.flowersoflife.core.domain.model.GoalStatus
import com.kuzmin.flowersoflife.core.domain.model.Task
import com.kuzmin.flowersoflife.core.domain.model.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.model.Wallet
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.feature.home.ui.model.ChildUi
import java.time.LocalDateTime

val mockUser = User(
    userId = "child_001",
    name = "Алиса",
    email = "alice@example.com",
    emailVerified = true,
    role = UserRole.CHILD,
    isAdmin = false,
    createdAt = System.currentTimeMillis() - 86400000L * 30, // 30 days ago
    updatedAt = System.currentTimeMillis(),
    familyId = "family_001",
    password = "",
    avatarUrl = null
)

val mockWallet = Wallet(
    walletId = "wallet_001",
    balance = 250,
    userId = "child_001"
)

val mockTasks = listOf<Task>(
    Task(
        taskId = "task_001",
        startDate = LocalDateTime.now().minusDays(1),
        endDate = LocalDateTime.now().plusDays(1),
        description = "Убрать комнату",
        status = TaskStatus.IN_PROGRESS,
        reward = 50,
        fine = 0,
        parentId = "parent_001",
        childId = "child_001"
    ),
    Task(
        taskId = "task_002",
        startDate = LocalDateTime.now().minusDays(3),
        endDate = LocalDateTime.now().minusDays(1),
        description = "Погулять с собакой",
        status = TaskStatus.COMPLETED,
        reward = 30,
        fine = 0,
        parentId = "parent_001",
        childId = "child_001"
    ),
    Task(
        taskId = "task_003",
        startDate = LocalDateTime.now(),
        endDate = LocalDateTime.now().plusDays(2),
        description = "Помыть посуду",
        status = TaskStatus.PENDING,
        reward = 20,
        fine = 10,
        parentId = "parent_001",
        childId = "child_001"
    )
)

val mockGoals = listOf<Goal>(
    Goal(
        goalId = "goal_001",
        price = 1500,
        name = "Наушники",
        status = GoalStatus.CREATED,
        childId = "child_001"
    ),
    Goal(
        goalId = "goal_002",
        price = 600,
        name = "Настольная игра",
        status = GoalStatus.ACCEPTED,
        childId = "child_001"
    ),
    Goal(
        goalId = "goal_003",
        price = 1200,
        name = "Скетчбук",
        status = GoalStatus.COMPLETED,
        childId = "child_001"
    )
)

val mockFinances = listOf<FinancialRecord>(
    FinancialRecord(
        opId = "fin_001",
        type = FinanceRecordType.DEPOSIT,
        amount = 100,
        rate = 0,
        description = "Пополнение от родителей",
        startDate = LocalDateTime.now().minusDays(5),
        endDate = LocalDateTime.now().minusDays(5),
        childId = "child_001"
    ),
    FinancialRecord(
        opId = "fin_002",
        type = FinanceRecordType.CREDIT,
        amount = 50,
        rate = 0,
        description = "Покупка игрушки",
        startDate = LocalDateTime.now().minusDays(2),
        endDate = LocalDateTime.now().minusDays(2),
        childId = "child_001"
    )
)

val mockChildDashboard = ChildDashboard(
    user = mockUser,
    wallet = mockWallet,
    tasks = mockTasks,
    goals = mockGoals,
    financialRecords = mockFinances
)

val mockChildUi = ChildUi(
    childId = "1",
    childName = "Gonzo",
    balance = 0
)