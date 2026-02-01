package com.kuzmin.flowersoflife.feature.home.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.Task
import com.kuzmin.flowersoflife.core.domain.model.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters


class GetTasksByPeriodUseCase {

    /**
     * Получает задачи за указанный период с учетом смещения
     *
     * @param dashboard данные дашборда ребенка
     * @param period тип периода (неделя, месяц, год)
     * @param periodIndex смещение периода (0 = текущий, -1 = предыдущий, +1 = следующий)
     * @param status опциональный фильтр по статусу задачи
     * @return список задач, соответствующих критериям
     */
    operator fun invoke(
        dashboard: ChildDashboard,
        period: TimePeriod,
        periodIndex: Int = 0,
        status: TaskStatus? = null
    ): List<Task> {
        val tasks = when (period) {
            TimePeriod.WEEK -> getTasksForWeek(dashboard.tasks, periodIndex)
            TimePeriod.MONTH -> getTasksForMonth(dashboard.tasks, periodIndex)
            TimePeriod.YEAR -> getTasksForYear(dashboard.tasks, periodIndex)
        }

        return status?.let {
            tasks.filter { task -> task.status == it }
        } ?: tasks
    }

    /**
     * Получает задачи за неделю с учетом смещения
     * @param periodIndex 0 = текущая неделя, -1 = прошлая неделя, 1 = следующая неделя
     */
    private fun getTasksForWeek(tasks: List<Task>, periodIndex: Int): List<Task> {
        val today = LocalDate.now().plusWeeks(periodIndex.toLong())
        val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

        return tasks.filter { task ->
            val taskDate = task.startDate.toLocalDate()
            taskDate in startOfWeek..endOfWeek
        }
    }

    /**
     * Получает задачи за месяц с учетом смещения
     * @param periodIndex 0 = текущий месяц, -1 = прошлый месяц, 1 = следующий месяц
     */
    private fun getTasksForMonth(tasks: List<Task>, periodIndex: Int): List<Task> {
        val targetMonth = YearMonth.now().plusMonths(periodIndex.toLong())
        val startOfMonth = targetMonth.atDay(1)
        val endOfMonth = targetMonth.atEndOfMonth()

        return tasks.filter { task ->
            val taskDate = task.startDate.toLocalDate()
            taskDate in startOfMonth..endOfMonth
        }
    }

    /**
     * Получает задачи за год с учетом смещения
     * @param periodIndex 0 = текущий год, -1 = прошлый год, 1 = следующий год
     */
    private fun getTasksForYear(tasks: List<Task>, periodIndex: Int): List<Task> {
        val targetYear = LocalDate.now().year + periodIndex
        val startOfYear = LocalDate.of(targetYear, 1, 1)
        val endOfYear = LocalDate.of(targetYear, 12, 31)

        return tasks.filter { task ->
            val taskDate = task.startDate.toLocalDate()
            taskDate in startOfYear..endOfYear
        }
    }

    /**
     * Вспомогательный метод для получения диапазона дат периода
     * Полезен для отображения в UI (например, "1 янв - 7 янв 2026")
     */
    fun getPeriodRange(period: TimePeriod, periodIndex: Int): PeriodRange {
        return when (period) {
            TimePeriod.WEEK -> {
                val today = LocalDate.now().plusWeeks(periodIndex.toLong())
                val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                val endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                PeriodRange(startOfWeek, endOfWeek)
            }
            TimePeriod.MONTH -> {
                val targetMonth = YearMonth.now().plusMonths(periodIndex.toLong())
                val startOfMonth = targetMonth.atDay(1)
                val endOfMonth = targetMonth.atEndOfMonth()
                PeriodRange(startOfMonth, endOfMonth)
            }
            TimePeriod.YEAR -> {
                val targetYear = LocalDate.now().year + periodIndex
                val startOfYear = LocalDate.of(targetYear, 1, 1)
                val endOfYear = LocalDate.of(targetYear, 12, 31)
                PeriodRange(startOfYear, endOfYear)
            }
        }
    }
}

/**
 * Enum для типов периодов
 */
enum class TimePeriod {
    WEEK,
    MONTH,
    YEAR
}

/**
 * Data class для диапазона дат периода
 */
data class PeriodRange(
    val start: LocalDate,
    val end: LocalDate
)