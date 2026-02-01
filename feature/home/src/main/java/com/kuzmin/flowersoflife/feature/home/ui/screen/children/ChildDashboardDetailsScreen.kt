package com.kuzmin.flowersoflife.feature.home.ui.screen.children

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kuzmin.flowersoflife.common.ext.toDisplayedDate
import com.kuzmin.flowersoflife.common.ext.toDisplayedShortDate
import com.kuzmin.flowersoflife.common.ext.toMonthName
import com.kuzmin.flowersoflife.core.domain.extensions.getAllTasksByMonth
import com.kuzmin.flowersoflife.core.domain.extensions.getAllTasksByWeek
import com.kuzmin.flowersoflife.core.domain.extensions.getAllTasksByYear
import com.kuzmin.flowersoflife.core.domain.extensions.getCompletedTasksByMonth
import com.kuzmin.flowersoflife.core.domain.extensions.getCompletedTasksByWeek
import com.kuzmin.flowersoflife.core.domain.extensions.getCompletedTasksByYear
import com.kuzmin.flowersoflife.core.domain.extensions.getFailedTasksByMonth
import com.kuzmin.flowersoflife.core.domain.extensions.getFailedTasksByWeek
import com.kuzmin.flowersoflife.core.domain.extensions.getFailedTasksByYear
import com.kuzmin.flowersoflife.core.domain.model.FinanceRecordType
import com.kuzmin.flowersoflife.core.domain.model.FinancialRecord
import com.kuzmin.flowersoflife.core.domain.model.GoalStatus
import com.kuzmin.flowersoflife.core.domain.model.TaskStatus
import com.kuzmin.flowersoflife.core.domain.model.aggregate.ChildDashboard
import com.kuzmin.flowersoflife.core.ui.R
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.Regular12
import com.kuzmin.flowersoflife.core.ui.theme.Regular16
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold16
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold18
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold20
import com.kuzmin.flowersoflife.feature.home.ui.component.DashboardChart
import com.kuzmin.flowersoflife.feature.home.ui.mock.mockChartDataWeek
import com.kuzmin.flowersoflife.feature.home.ui.mock.mockChildDashboardExtended
import com.kuzmin.flowersoflife.feature.home.ui.mock.mockChildDashboardRealistic
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartDashboardGraphicsData
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartPeriod
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildState
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.ChildDashboardDetailsViewModel
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.contract.OnChartAction
import org.koin.androidx.compose.koinViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

@Composable
fun ChildDashboardDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ChildDashboardDetailsViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsState()
    val chartData by viewModel.chartData.collectAsState()

    val onChartAction = viewModel.onChartAction

    when (val uiState = state) {
        is BaseChildState.Loading -> {
        }
        is BaseChildState.Success -> {
            ChildDashboardDetailsScreenContent(
                modifier = modifier,
                childDashboard = uiState.data,
                chartData = chartData,
                onChartAction = onChartAction
            )
        }
        is BaseChildState.Error -> {
        }
    }
}

@Composable
fun ChildDashboardDetailsScreenContent(
    modifier: Modifier = Modifier,
    childDashboard: ChildDashboard,
    chartData: ChartDashboardGraphicsData?,
    onChartAction: OnChartAction,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        KabTheme.colors.cardDetailsGradStart,
                        KabTheme.colors.cardDetailsGradEnd,
                    )
                )
            )
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = childDashboard.user.avatarUrl,
                    contentDescription = "Avatar ${childDashboard.user.name}",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.avatar_placeholder),
                    error = painterResource(id = R.drawable.avatar_placeholder)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "${childDashboard.user.name}:",
                        color = KabTheme.colors.primaryOnCardText,
                        style = SemiBold20
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    StatRow(
                        valueTextColor = KabTheme.colors.successText,
                        labelRes = R.string.child_card_earned_coins,
                        value = childDashboard.wallet.balance.toString()
                    )

                    val goal = childDashboard.goals.find { it.status == GoalStatus.ACCEPTED }
                    StatRow(
                        valueTextColor = KabTheme.colors.primaryOnCardText,
                        labelRes = R.string.child_card_goal,
                        value = goal?.name ?: stringResource(id = R.string.child_card_goal_undefined)
                    )

                    goal?.let {
                        StatRow(
                            valueTextColor = KabTheme.colors.infoText,
                            labelRes = R.string.dashboard_goal_price,
                            value = it.price.toString()
                        )
                    }
                }
            }

            TableTitle(
                textRes = R.string.child_card_deposits_credits
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
               childDashboard.financialRecords.forEach { record ->
                   FinancialRecordStat(record = record)
               }
            }

            TableTitle(
                textRes = R.string.child_card_task_stats_title
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TaskStatHeader(
                    chartData = chartData
                )

                TaskStatItem(
                    modifier = Modifier.fillMaxWidth(),
                    status = null,
                    dashboard = childDashboard,
                    weekIndex = chartData?.weekIndex ?: 0,
                    monthIndex = chartData?.monthIndex ?: 0,
                    yearIndex = chartData?.yearIndex ?: 0
                )

                TaskStatItem(
                    modifier = Modifier.fillMaxWidth(),
                    status = TaskStatus.COMPLETED,
                    dashboard = childDashboard,
                    weekIndex = chartData?.weekIndex ?: 0,
                    monthIndex = chartData?.monthIndex ?: 0,
                    yearIndex = chartData?.yearIndex ?: 0
                )

                TaskStatItem(
                    modifier = Modifier.fillMaxWidth(),
                    status = TaskStatus.FAILED,
                    dashboard = childDashboard,
                    weekIndex = chartData?.weekIndex ?: 0,
                    monthIndex = chartData?.monthIndex ?: 0,
                    yearIndex = chartData?.yearIndex ?: 0
                )

                TaskRewardItem(
                    modifier = Modifier.fillMaxWidth(),
                    dashboard = childDashboard,
                    weekIndex = chartData?.weekIndex ?: 0,
                    monthIndex = chartData?.monthIndex ?: 0,
                    yearIndex = chartData?.yearIndex ?: 0,
                    showReward = true,
                    titleRes = R.string.dashboard_earned,
                    color = KabTheme.colors.successText
                )

                TaskRewardItem(
                    modifier = Modifier.fillMaxWidth(),
                    dashboard = childDashboard,
                    weekIndex = chartData?.weekIndex ?: 0,
                    monthIndex = chartData?.monthIndex ?: 0,
                    yearIndex = chartData?.yearIndex ?: 0,
                    showReward = false,
                    titleRes = R.string.dashboard_fines,
                    color = KabTheme.colors.errorText
                )
            }

            chartData?.let { data ->
                DashboardChart(
                    chartData = data,
                    onPeriodChange = onChartAction::onChartPeriodChange,
                    onNavigatePrevious = onChartAction::onChartNavigatePrevious,
                    onNavigateNext = onChartAction::onChartNavigateNext
                )
            }
        }
    }
}

@Composable
private fun StatRow(
    modifier: Modifier = Modifier,
    valueTextColor: Color = KabTheme.colors.primaryOnCardText,
    valueTextStyle: TextStyle = SemiBold18,
    textColor: Color = KabTheme.colors.primaryOnCardText,
    textStyle: TextStyle = Regular16,
    labelRes: Int,
    value: String,
) {
    Row(
        modifier = Modifier
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = stringResource(labelRes),
            color = textColor,
            style = textStyle,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                color = valueTextColor,
                style = valueTextStyle
            )

            Box(
                modifier = Modifier
                    .size(16.dp),
                contentAlignment = Alignment.Center
            ) {
                val painterRes = when (labelRes) {
                    R.string.child_card_earned_coins -> R.drawable.two_coins
                    R.string.dashboard_goal_price -> R.drawable.coin_single
                    else -> null
                }

                val tintColor = when (labelRes) {
                    R.string.child_card_earned_coins -> KabTheme.colors.warningText
                    R.string.dashboard_goal_price -> Color.Unspecified
                    else -> Color.Transparent
                }

                painterRes?.let {
                    Icon(
                        modifier = if (it == R.drawable.coin_single) Modifier.size(12.dp) else Modifier,
                        painter = painterResource(painterRes),
                        tint = tintColor,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun FinancialRecordStat(
    modifier: Modifier = Modifier,
    record: FinancialRecord
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TagChip(
            modifier = Modifier
                .weight(1f),
            type = record.type,
            tag = record.type.value.replaceFirstChar { it.uppercase() }
        )

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = record.amount.toString(),
                    style = SemiBold16,
                    color = KabTheme.colors.primaryOnCardText
                )
                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.coin_single),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }

        Text(
            modifier = Modifier.weight(1f),
            text = "${record.rate} %",
            style = SemiBold16,
            color = when(record.type) {
                FinanceRecordType.CREDIT -> KabTheme.colors.errorText
                FinanceRecordType.DEPOSIT -> KabTheme.colors.successText
            },
            textAlign = TextAlign.Center
        )

        val date = record.endDate.toLocalDate().toDisplayedDate()
        Text(
            modifier = Modifier.weight(2f),
            text = "до $date",
            style = SemiBold16,
            color = KabTheme.colors.primaryOnCardText,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TagChip(
    modifier: Modifier = Modifier,
    type: FinanceRecordType,
    tag: String
) {
    val color = when (type) {
        FinanceRecordType.CREDIT -> KabTheme.colors.redPaleTag
        FinanceRecordType.DEPOSIT -> KabTheme.colors.yellowPaleTag
    }
    Text(
        text = tag,
        color = KabTheme.colors.primaryOnCardText,
        style = SemiBold16,
        textAlign = TextAlign.Center,
        modifier = modifier.background(
            color = color,
            shape = RoundedCornerShape(8.dp)
        ).padding(horizontal = 4.dp, vertical = 4.dp)
    )
}

@Composable
private fun TableTitle(
    textRes: Int,
) {
    Text(
        text = stringResource(id = textRes),
        color = KabTheme.colors.primaryOnCardText,
        style = SemiBold18
    )
}

@Composable
private fun TaskStatHeader(
    chartData: ChartDashboardGraphicsData?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val dividerHeight = 40.dp
        Text(
            modifier = Modifier.weight(1.7f),
            text = stringResource(id = R.string.dashboard_task_header_statistics),
            color = KabTheme.colors.primaryOnCardText,
            style = SemiBold16,
            textAlign = TextAlign.Start
        )
        VerticalDivider(
            thickness = 1.dp,
            color = KabTheme.colors.frameInactive,
            modifier = Modifier.height(dividerHeight)
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(id = R.string.dashboard_task_header_week),
                color = KabTheme.colors.primaryOnCardText,
                style = SemiBold16,
                textAlign = TextAlign.Center
            )

            chartData?.let { data ->
                val today = LocalDate.now().plusWeeks(data.weekIndex.toLong())
                val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                val endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

                val startDate = startOfWeek.toDisplayedShortDate()
                val endDate = endOfWeek.toDisplayedShortDate()

                Text(
                    text = "$startDate - $endDate",
                    color = if (data.selectedPeriod == ChartPeriod.WEEK) {
                        KabTheme.colors.primaryOnCardText.copy(alpha = 0.8f)
                    } else {
                        KabTheme.colors.primaryOnCardText.copy(alpha = 0.5f)
                    },
                    style = Regular12,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

        }

        VerticalDivider(
            thickness = 1.dp,
            color = KabTheme.colors.frameInactive,
            modifier = Modifier.height(dividerHeight)
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(id = R.string.dashboard_task_header_month),
                color = KabTheme.colors.primaryOnCardText,
                style = SemiBold16,
                textAlign = TextAlign.Center
            )

            chartData?.let { data ->
                val targetMonth = YearMonth.now().plusMonths(data.monthIndex.toLong())
                val monthName = targetMonth.toMonthName()

                Text(
                    text = monthName.replaceFirstChar { it.uppercase() },
                    color = if (data.selectedPeriod == ChartPeriod.MONTH) {
                        KabTheme.colors.primaryOnCardText.copy(alpha = 0.8f)
                    } else {
                        KabTheme.colors.primaryOnCardText.copy(alpha = 0.5f)
                    },
                    style = Regular12,
                    textAlign = TextAlign.Center
                )
            }
        }

        VerticalDivider(
            thickness = 1.dp,
            color = KabTheme.colors.frameInactive,
            modifier = Modifier.height(dividerHeight)
        )

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(id = R.string.dashboard_task_header_year),
                color = KabTheme.colors.primaryOnCardText,
                style = SemiBold16,
                textAlign = TextAlign.Center
            )

            chartData?.let { data ->
                val targetYear = LocalDate.now().year + data.yearIndex

                Text(
                    text = targetYear.toString(),
                    color = if (data.selectedPeriod == ChartPeriod.YEAR) {
                        KabTheme.colors.primaryOnCardText.copy(alpha = 0.8f)
                    } else {
                        KabTheme.colors.primaryOnCardText.copy(alpha = 0.5f)
                    },
                    style = Regular12,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Composable
private fun TaskStatItem(
    modifier: Modifier = Modifier,
    status: TaskStatus? = null,
    dashboard: ChildDashboard,
    weekIndex: Int = 0,
    monthIndex: Int = 0,
    yearIndex: Int = 0
) {
    val dividerHeight = 26.dp
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        val textRes = when(status) {
            TaskStatus.COMPLETED -> R.string.child_card_closed_tasks
            TaskStatus.FAILED -> R.string.child_card_failed_tasks
            else -> R.string.child_card_created_tasks
        }
        Text(
            modifier = Modifier.weight(1.7f),
            text = stringResource(id = textRes),
            color = KabTheme.colors.primaryOnCardText,
            style = Regular16,
            textAlign = TextAlign.Start
        )
        VerticalDivider(
            thickness = 1.dp,
            color = KabTheme.colors.frameInactive,
            modifier = Modifier.height(dividerHeight)
        )
        val weekTasks = when(status) {
            TaskStatus.COMPLETED -> dashboard.getCompletedTasksByWeek(weekIndex)
            TaskStatus.FAILED -> dashboard.getFailedTasksByWeek(weekIndex)
            else -> dashboard.getAllTasksByWeek(weekIndex)
        }
        Text(
            modifier = Modifier.weight(1f),
            text = weekTasks.size.toString(),
            color = KabTheme.colors.primaryOnCardText,
            style = Regular16,
            textAlign = TextAlign.Center
        )
        VerticalDivider(
            thickness = 1.dp,
            color = KabTheme.colors.frameInactive,
            modifier = Modifier.height(dividerHeight)
        )
        val monthTasks = when(status) {
            TaskStatus.COMPLETED -> dashboard.getCompletedTasksByMonth(monthIndex)
            TaskStatus.FAILED -> dashboard.getFailedTasksByMonth(monthIndex)
            else -> dashboard.getAllTasksByMonth(monthIndex)
        }
        Text(
            modifier = Modifier.weight(1f),
            text = monthTasks.size.toString(),
            color = KabTheme.colors.primaryOnCardText,
            style = Regular16,
            textAlign = TextAlign.Center
        )
        VerticalDivider(
            thickness = 1.dp,
            color = KabTheme.colors.frameInactive,
            modifier = Modifier.height(dividerHeight)
        )
        val yearTasks = when(status) {
            TaskStatus.COMPLETED -> dashboard.getCompletedTasksByYear(yearIndex)
            TaskStatus.FAILED -> dashboard.getFailedTasksByYear(yearIndex)
            else -> dashboard.getAllTasksByYear(yearIndex)
        }
        Text(
            modifier = Modifier.weight(1f),
            text = yearTasks.size.toString(),
            color = KabTheme.colors.primaryOnCardText,
            style = Regular16,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TaskRewardItem(
    modifier: Modifier = Modifier,
    dashboard: ChildDashboard,
    weekIndex: Int = 0,
    monthIndex: Int = 0,
    yearIndex: Int = 0,
    showReward: Boolean,
    titleRes: Int,
    color: Color
) {
    val dividerHeight = 26.dp
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            modifier = Modifier.weight(1.7f),
            text = stringResource(id = titleRes),
            color = color,
            style = Regular16,
            textAlign = TextAlign.Start
        )
        VerticalDivider(
            thickness = 1.dp,
            color = KabTheme.colors.frameInactive,
            modifier = Modifier.height(dividerHeight)
        )
        val weekReward = when(showReward) {
            true -> dashboard.getCompletedTasksByWeek(weekIndex).sumOf { it.reward }
            else -> dashboard.getFailedTasksByWeek(weekIndex).sumOf { it.fine }
        }
        Text(
            modifier = Modifier.weight(1f),
            text = weekReward.toString(),
            color = color,
            style = Regular16,
            textAlign = TextAlign.Center
        )
        VerticalDivider(
            thickness = 1.dp,
            color = KabTheme.colors.frameInactive,
            modifier = Modifier.height(dividerHeight)
        )
        val monthReward = when(showReward) {
            true -> dashboard.getCompletedTasksByMonth(monthIndex).sumOf { it.reward }
            else -> dashboard.getFailedTasksByMonth(monthIndex).sumOf { it.fine }
        }
        Text(
            modifier = Modifier.weight(1f),
            text = monthReward.toString(),
            color = color,
            style = Regular16,
            textAlign = TextAlign.Center
        )
        VerticalDivider(
            thickness = 1.dp,
            color = KabTheme.colors.frameInactive,
            modifier = Modifier.height(dividerHeight)
        )
        val yearReward = when(showReward) {
            true -> dashboard.getCompletedTasksByYear(yearIndex).sumOf { it.reward }
            else -> dashboard.getFailedTasksByYear(yearIndex).sumOf { it.fine }
        }
        Text(
            modifier = Modifier.weight(1f),
            text = yearReward.toString(),
            color = color,
            style = Regular16,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF1A1A2E,
    device = "spec:width=411dp,height=891dp"
)
@Composable
private fun ChildDashboardDetailsScreenContentPreview() {
    KabTheme {
        ChildDashboardDetailsScreenContent(
            childDashboard = mockChildDashboardRealistic,
            chartData = mockChartDataWeek,
            onChartAction = object : OnChartAction {
                override fun onChartNavigateNext() {
                }

                override fun onChartNavigatePrevious() {
                }

                override fun onChartPeriodChange(period: ChartPeriod) {
                }
            }
        )
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun TaskStatItemPreview() {
    KabTheme {
        TaskStatItem(
            modifier = Modifier.fillMaxWidth(),
            dashboard = mockChildDashboardExtended,
            status = null,
        )
    }
}
