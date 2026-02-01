package com.kuzmin.flowersoflife.core.domain.extensions

import com.kuzmin.flowersoflife.core.domain.model.Task
import com.kuzmin.flowersoflife.core.domain.model.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale


fun ChildDashboard.getAllTasksByWeek(weekIndex: Int = 0): List<Task> {
    return getTasksByPeriod(TimePeriod.WEEK, null, weekIndex)
}

fun ChildDashboard.getCompletedTasksByWeek(weekIndex: Int = 0): List<Task> {
    return getTasksByPeriod(TimePeriod.WEEK, TaskStatus.COMPLETED, weekIndex)
}

fun ChildDashboard.getFailedTasksByWeek(weekIndex: Int = 0): List<Task> {
    return getTasksByPeriod(TimePeriod.WEEK, TaskStatus.FAILED, weekIndex)
}

fun ChildDashboard.getAllTasksByMonth(monthIndex: Int = 0): List<Task> {
    return getTasksByPeriod(TimePeriod.MONTH, null, monthIndex)
}

fun ChildDashboard.getCompletedTasksByMonth(monthIndex: Int = 0): List<Task> {
    return getTasksByPeriod(TimePeriod.MONTH, TaskStatus.COMPLETED, monthIndex)
}

fun ChildDashboard.getFailedTasksByMonth(monthIndex: Int = 0): List<Task> {
    return getTasksByPeriod(TimePeriod.MONTH, TaskStatus.FAILED, monthIndex)
}

fun ChildDashboard.getAllTasksByYear(yearIndex: Int = 0): List<Task> {
    return getTasksByPeriod(TimePeriod.YEAR, null, yearIndex)
}

fun ChildDashboard.getCompletedTasksByYear(yearIndex: Int = 0): List<Task> {
    return getTasksByPeriod(TimePeriod.YEAR, TaskStatus.COMPLETED, yearIndex)
}

fun ChildDashboard.getFailedTasksByYear(yearIndex: Int = 0): List<Task> {
    return getTasksByPeriod(TimePeriod.YEAR, TaskStatus.FAILED, yearIndex)
}

private fun ChildDashboard.getTasksByPeriod(
    period: TimePeriod,
    status: TaskStatus?,
    periodIndex: Int = 0
): List<Task> {
    val (startDate, endDate) = when (period) {
        TimePeriod.WEEK -> {
            val today = LocalDate.now().plusWeeks(periodIndex.toLong())
            val start = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            val end = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            start to end
        }
        TimePeriod.MONTH -> {
            val month = YearMonth.now().plusMonths(periodIndex.toLong())
            month.atDay(1) to month.atEndOfMonth()
        }
        TimePeriod.YEAR -> {
            val year = LocalDate.now().year + periodIndex
            LocalDate.of(year, 1, 1) to LocalDate.of(year, 12, 31)
        }
    }

    return tasks.filter { task ->
        val taskDate = task.startDate.toLocalDate()
        val matchesStatus = status?.let { task.status == it } ?: true
        matchesStatus && taskDate in startDate..endDate
    }
}

fun LocalDate.toShortFormat(): String {
    return format(DateTimeFormatter.ofPattern("dd.MM"))
}

fun LocalDate.toMonthName(): String {
    return format(DateTimeFormatter.ofPattern("LLLL", Locale.getDefault()))
}

fun Pair<LocalDate, LocalDate>.toWeekRangeFormat(): String {
    return "${first.toShortFormat()} - ${second.toShortFormat()}"
}

private enum class TimePeriod {
    WEEK, MONTH, YEAR
}