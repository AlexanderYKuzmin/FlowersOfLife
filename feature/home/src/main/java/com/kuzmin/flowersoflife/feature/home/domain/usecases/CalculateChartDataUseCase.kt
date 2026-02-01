package com.kuzmin.flowersoflife.core.domain.usecases.dashboard

import com.kuzmin.flowersoflife.core.domain.model.Task
import com.kuzmin.flowersoflife.core.domain.model.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.feature.home.domain.usecases.PeriodRange
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartDashboardGraphicsData
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartDataPoint
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartPeriod
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

class CalculateChartDataUseCase {

    operator fun invoke(
        dashboard: ChildDashboard,
        period: ChartPeriod,
        periodIndex: Int = 0,
        weekIndex: Int = 0,
        monthIndex: Int = 0,
        yearIndex: Int = 0
    ): ChartDashboardGraphicsData {
        return when (period) {
            ChartPeriod.WEEK -> calculateWeekData(dashboard, weekIndex, monthIndex, yearIndex)
            ChartPeriod.MONTH -> calculateMonthData(dashboard, weekIndex, monthIndex, yearIndex)
            ChartPeriod.YEAR -> calculateYearData(dashboard, weekIndex, monthIndex, yearIndex)
        }
    }

    /**
     * Рассчитывает данные для недельного графика (7 дней, шаг = 1 день)
     */
    private fun calculateWeekData(
        dashboard: ChildDashboard,
        weekIndex: Int,
        monthIndex: Int,
        yearIndex: Int
    ): ChartDashboardGraphicsData {
        val today = LocalDate.now().plusWeeks(weekIndex.toLong())
        val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

        val dataPoints = mutableListOf<ChartDataPoint>()

        var currentDate = startOfWeek
        while (!currentDate.isAfter(endOfWeek)) {
            val dayTasks = dashboard.tasks.filter { task ->
                task.startDate.toLocalDate() == currentDate
            }

            dataPoints.add(
                ChartDataPoint(
                    label = currentDate.dayOfWeek.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    ), // Пн, Вт, Ср...
                    completedTasks = dayTasks.count { it.status == TaskStatus.COMPLETED },
                    failedTasks = dayTasks.count { it.status == TaskStatus.FAILED },
                    earnedCoins = dayTasks
                        .filter { it.status == TaskStatus.COMPLETED }
                        .sumOf { it.reward },
                    coins = calculateNetCoins(dayTasks)
                )
            )

            currentDate = currentDate.plusDays(1)
        }

        return ChartDashboardGraphicsData(
            data = dataPoints,
            selectedPeriod = ChartPeriod.WEEK,
            selectedIndex = weekIndex,
            range = PeriodRange(startOfWeek, endOfWeek),
            step = 1,
            weekIndex = weekIndex,
            monthIndex = monthIndex,
            yearIndex = yearIndex
        )
    }

    /**
     * Рассчитывает данные для месячного графика (все дни месяца, шаг = 1 день)
     */
    private fun calculateMonthData(
        dashboard: ChildDashboard,
        weekIndex: Int,
        monthIndex: Int,
        yearIndex: Int
    ): ChartDashboardGraphicsData {
        val targetMonth = YearMonth.now().plusMonths(monthIndex.toLong())
        val startOfMonth = targetMonth.atDay(1)
        val endOfMonth = targetMonth.atEndOfMonth()

        val dataPoints = mutableListOf<ChartDataPoint>()

        var currentDate = startOfMonth
        while (!currentDate.isAfter(endOfMonth)) {
            val dayTasks = dashboard.tasks.filter { task ->
                task.startDate.toLocalDate() == currentDate
            }

            dataPoints.add(
                ChartDataPoint(
                    label = currentDate.dayOfMonth.toString(), // 1, 2, 3...
                    completedTasks = dayTasks.count { it.status == TaskStatus.COMPLETED },
                    failedTasks = dayTasks.count { it.status == TaskStatus.FAILED },
                    earnedCoins = dayTasks
                        .filter { it.status == TaskStatus.COMPLETED }
                        .sumOf { it.reward },
                    coins = calculateNetCoins(dayTasks)
                )
            )

            currentDate = currentDate.plusDays(1)
        }

        return ChartDashboardGraphicsData(
            data = dataPoints,
            selectedPeriod = ChartPeriod.MONTH,
            selectedIndex = monthIndex,
            range = PeriodRange(startOfMonth, endOfMonth),
            step = 1,
            weekIndex = weekIndex,
            monthIndex = monthIndex,
            yearIndex = yearIndex
        )
    }

    /**
     * Рассчитывает данные для годового графика (12 месяцев, шаг = 1 месяц)
     */
    private fun calculateYearData(
        dashboard: ChildDashboard,
        weekIndex: Int,
        monthIndex: Int,
        yearIndex: Int
    ): ChartDashboardGraphicsData {
        val targetYear = LocalDate.now().year + yearIndex
        val startOfYear = LocalDate.of(targetYear, 1, 1)
        val endOfYear = LocalDate.of(targetYear, 12, 31)

        val dataPoints = mutableListOf<ChartDataPoint>()

        for (month in 1..12) {
            val yearMonth = YearMonth.of(targetYear, month)
            val monthStart = yearMonth.atDay(1)
            val monthEnd = yearMonth.atEndOfMonth()

            val monthTasks = dashboard.tasks.filter { task ->
                val taskDate = task.startDate.toLocalDate()
                taskDate in monthStart..monthEnd
            }

            dataPoints.add(
                ChartDataPoint(
                    label = yearMonth.month.getDisplayName(
                        TextStyle.SHORT,
                        Locale.getDefault()
                    ), // Янв, Фев, Мар...
                    completedTasks = monthTasks.count { it.status == TaskStatus.COMPLETED },
                    failedTasks = monthTasks.count { it.status == TaskStatus.FAILED },
                    earnedCoins = monthTasks
                        .filter { it.status == TaskStatus.COMPLETED }
                        .sumOf { it.reward },
                    coins = calculateNetCoins(monthTasks)
                )
            )
        }

        return ChartDashboardGraphicsData(
            data = dataPoints,
            selectedPeriod = ChartPeriod.YEAR,
            selectedIndex = yearIndex,
            range = PeriodRange(startOfYear, endOfYear),
            step = 30,
            weekIndex = weekIndex,
            monthIndex = monthIndex,
            yearIndex = yearIndex
        )
    }

    /**
     * Рассчитывает чистые монеты (заработанные - потерянные)
     */
    private fun calculateNetCoins(tasks: List<Task>): Int {
        val earned = tasks
            .filter { it.status == TaskStatus.COMPLETED }
            .sumOf { it.reward }

        val lost = tasks
            .filter { it.status == TaskStatus.FAILED }
            .sumOf { it.fine }

        return earned - lost
    }
}