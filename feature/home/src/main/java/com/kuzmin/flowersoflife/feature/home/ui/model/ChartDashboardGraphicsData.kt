package com.kuzmin.flowersoflife.feature.home.ui.model

import com.kuzmin.flowersoflife.feature.home.domain.usecases.PeriodRange
import java.util.Calendar

data class ChartDashboardGraphicsData(
    val data: List<ChartDataPoint>,
    val selectedPeriod: ChartPeriod = ChartPeriod.MONTH,
    val selectedIndex: Int = 0,
    val range: PeriodRange,
    val step: Int,
    val selectedYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val weekIndex: Int = 0,
    val monthIndex: Int = 0,
    val yearIndex: Int = 0
)

data class ChartDataPoint(
    val label: String,
    val completedTasks: Int,
    val failedTasks: Int,
    val earnedCoins: Int,
    val coins: Int
)

enum class ChartPeriod {
    WEEK, MONTH, YEAR
}

enum class ChartStep {
    DAY, MONTH, YEAR
}

enum class ChartDashboardAction {
    PREV, NEXT, PERIOD
}
