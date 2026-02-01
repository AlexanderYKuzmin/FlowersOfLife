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
import com.kuzmin.flowersoflife.feature.home.domain.usecases.PeriodRange
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartDashboardGraphicsData
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartDataPoint
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartPeriod
import com.kuzmin.flowersoflife.feature.home.ui.model.ChildUi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.YearMonth

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

val mockFinancesForPreview = listOf(
    // Депозит 1
    FinancialRecord(
        opId = "fin_001",
        type = FinanceRecordType.DEPOSIT,
        amount = 500,
        rate = 5,
        description = "Депозит на полгода",
        startDate = LocalDateTime.now().minusMonths(1),
        endDate = LocalDateTime.now().plusMonths(5),
        childId = "child_001"
    ),
    // Депозит 2
    FinancialRecord(
        opId = "fin_002",
        type = FinanceRecordType.DEPOSIT,
        amount = 300,
        rate = 3,
        description = "Краткосрочный депозит",
        startDate = LocalDateTime.now().minusDays(10),
        endDate = LocalDateTime.now().plusMonths(2),
        childId = "child_001"
    ),
    // Кредит
    FinancialRecord(
        opId = "fin_003",
        type = FinanceRecordType.CREDIT,
        amount = 200,
        rate = 10,
        description = "Кредит на игрушку",
        startDate = LocalDateTime.now().minusDays(15),
        endDate = LocalDateTime.now().plusMonths(1),
        childId = "child_001"
    )
)

//40 заданий с нужным распределением
val mockTasksExtended = buildList {
    val now = LocalDateTime.now()

    // 30 завершенных заданий
    repeat(30) { i ->
        add(Task(
            taskId = "task_completed_$i",
            startDate = now.minusDays((i % 90).toLong() + 1),
            endDate = now.minusDays((i % 90).toLong()),
            description = "Завершенное задание ${i + 1}",
            status = TaskStatus.COMPLETED,
            reward = 20 + (i % 30),
            fine = 0,
            parentId = "parent_001",
            childId = "child_001"
        ))
    }

    // 6 проваленных заданий
    repeat(6) { i ->
        add(Task(
            taskId = "task_failed_$i",
            startDate = now.minusDays((i * 10).toLong() + 5),
            endDate = now.minusDays((i * 10).toLong() + 3),
            description = "Проваленное задание ${i + 1}",
            status = TaskStatus.FAILED,
            reward = 0,
            fine = 15 + (i % 10),
            parentId = "parent_001",
            childId = "child_001"
        ))
    }

    // 4 задания на выполнении
    repeat(4) { i ->
        add(Task(
            taskId = "task_progress_$i",
            startDate = now.minusDays(i.toLong()),
            endDate = now.plusDays((i + 2).toLong()),
            description = "Задание в процессе ${i + 1}",
            status = TaskStatus.IN_PROGRESS,
            reward = 25 + (i * 5),
            fine = 10 + (i * 3),
            parentId = "parent_001",
            childId = "child_001"
        ))
    }
}

val mockChildDashboardExtended = ChildDashboard(
    user = mockUser.copy(name = "Алиса"),
    wallet = mockWallet.copy(balance = 1500),
    tasks = mockTasksExtended,
    goals = mockGoals,
    financialRecords = mockFinancesForPreview
)

val mockTasksForChartRealistic = buildList {
    val now = LocalDateTime.now()
    val currentYear = now.year

    // Описания заданий с разными наградами
    val taskTemplates = listOf(
        "Убрать комнату" to 50,
        "Помыть посуду" to 30,
        "Сделать домашнее задание" to 80,
        "Погулять с собакой" to 40,
        "Полить цветы" to 20,
        "Вынести мусор" to 25,
        "Помочь с ужином" to 60,
        "Убрать игрушки" to 30,
        "Почистить зубы" to 20,
        "Заправить постель" to 25,
        "Помыть окна" to 100,
        "Пропылесосить" to 40,
        "Покормить питомца" to 20,
        "Разложить вещи по местам" to 30,
        "Помочь младшему брату/сестре" to 50
    )

    var taskCounter = 0

    // Генерируем задания за весь год
    for (month in 1..12) {
        val daysInMonth = YearMonth.of(currentYear, month).lengthOfMonth()

        // Количество заданий в месяц зависит от месяца
        // Летом (июнь-август) меньше заданий, в учебное время больше
        val tasksPerMonth = when (month) {
            6, 7, 8 -> (30..45).random() // Летние каникулы
            12, 1 -> (35..50).random()   // Зимние каникулы и праздники
            else -> (50..65).random()    // Обычные месяцы
        }

        // Распределяем задания по дням месяца
        val tasksPerDay = mutableMapOf<Int, Int>()
        repeat(tasksPerMonth) {
            val day = (1..daysInMonth).random()
            tasksPerDay[day] = (tasksPerDay[day] ?: 0) + 1
        }

        // Создаем задания
        tasksPerDay.forEach { (day, count) ->
            repeat(count) {
                val taskDate = LocalDateTime.of(
                    currentYear,
                    month,
                    day,
                    (8..20).random(),
                    (0..59).random()
                )

                // Прогрессивное улучшение: в начале года больше проваленных, к концу меньше
                val failRate = when (month) {
                    1, 2 -> 20  // Начало года: 20% проваленных
                    3, 4, 5 -> 15  // Весна: 15%
                    6, 7, 8 -> 18  // Лето: немного больше
                    9, 10 -> 12    // Осень: меньше
                    11, 12 -> 10   // Конец года: всего 10%
                    else -> 15
                }

                val status = when ((1..100).random()) {
                    in 1..(100 - failRate - 5) -> TaskStatus.COMPLETED
                    in (100 - failRate - 4)..(100 - failRate) -> TaskStatus.IN_PROGRESS
                    else -> TaskStatus.FAILED
                }

                val (description, baseReward) = taskTemplates.random()
                val reward = baseReward + (-10..10).random() // Небольшая вариация
                val fine = (reward * 0.3).toInt() + (-5..5).random()

                add(
                    Task(
                        taskId = "task_chart_${taskCounter++}",
                        startDate = taskDate,
                        endDate = taskDate.plusDays((1..2).random().toLong()),
                        description = description,
                        status = status,
                        reward = reward.coerceAtLeast(10),
                        fine = if (status == TaskStatus.FAILED) fine.coerceAtLeast(5) else 0,
                        parentId = "parent_001",
                        childId = "child_001"
                    )
                )
            }
        }
    }

    // Сортируем по дате для удобства
}.sortedBy { it.startDate }

// Обновленный dashboard с реалистичными данными
val mockChildDashboardRealistic = ChildDashboard(
    user = mockUser.copy(name = "Алиса"),
    wallet = mockWallet.copy(balance = 3500), // Баланс от выполненных заданий за год
    tasks = mockTasksForChartRealistic,
    goals = mockGoals,
    financialRecords = mockFinancesForPreview
)

val mockChartDataWeek = ChartDashboardGraphicsData(
    data = listOf(
        ChartDataPoint(
            label = "Пн",
            completedTasks = 3,
            failedTasks = 1,
            earnedCoins = 120,
            coins = 100
        ),
        ChartDataPoint(
            label = "Вт",
            completedTasks = 2,
            failedTasks = 0,
            earnedCoins = 80,
            coins = 80
        ),
        ChartDataPoint(
            label = "Ср",
            completedTasks = 4,
            failedTasks = 2,
            earnedCoins = 150,
            coins = 100
        ),
        // ... остальные дни
    ),
    selectedPeriod = ChartPeriod.WEEK,
    selectedIndex = 0,
    range = PeriodRange(
        start = LocalDate.of(2026, Month.JANUARY, 26),
        end = LocalDate.of(2026, Month.FEBRUARY, 1)
    ),
step = 1
)