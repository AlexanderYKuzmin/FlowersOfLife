package com.kuzmin.flowersoflife.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.common.ext.toDisplayedDate
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold12
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartDashboardGraphicsData
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartPeriod
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DashboardChart(
    modifier: Modifier = Modifier,
    chartData: ChartDashboardGraphicsData,
    onPeriodChange: (ChartPeriod) -> Unit,
    onNavigatePrevious: () -> Unit,
    onNavigateNext: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PeriodSelector(
                selectedPeriod = chartData.selectedPeriod,
                onPeriodSelected = onPeriodChange,
                modifier = Modifier.weight(0.4f)
            )

            DateNavigator(
                chartData = chartData,
                onPrevious = onNavigatePrevious,
                onNext = onNavigateNext,
                modifier = Modifier.weight(0.6f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        VicoChart(
            chartData = chartData
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeriodSelector(
    selectedPeriod: ChartPeriod,
    onPeriodSelected: (ChartPeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .menuAnchor()
                .clip(RoundedCornerShape(12.dp))
                .background(KabTheme.colors.simpleCardBgd)
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = getPeriodLabel(selectedPeriod),
                    style = SemiBold12,
                    color = KabTheme.colors.primaryOnCardText
                )

                Icon(
                    painter = painterResource(android.R.drawable.arrow_down_float),
                    contentDescription = null,
                    tint = KabTheme.colors.primaryOnCardText,
                    modifier = Modifier
                        .size(12.dp)
                        .rotate(if (expanded) 180f else 0f)
                )
            }
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(KabTheme.colors.simpleCardBgd)
        ) {
            ChartPeriod.entries.forEach { period ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = getPeriodLabel(period),
                            color = KabTheme.colors.primaryOnCardText,
                            style = SemiBold12
                        )
                    },
                    onClick = {
                        onPeriodSelected(period)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
private fun getPeriodLabel(period: ChartPeriod): String {
    return when (period) {
        ChartPeriod.WEEK -> stringResource(R.string.chart_period_week)
        ChartPeriod.MONTH -> stringResource(R.string.chart_period_month)
        ChartPeriod.YEAR -> stringResource(R.string.chart_period_year)
    }
}

@Composable
fun DateNavigator(
    chartData: ChartDashboardGraphicsData,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(KabTheme.colors.simpleCardBgd)
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onPrevious,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.arrow_down_float),
                    contentDescription = "previous",
                    tint = KabTheme.colors.primaryOnCardText,
                    modifier = Modifier
                        .size(12.dp)
                        .rotate(90f)
                )
            }

            Text(
                text = formatNavigatorLabel(chartData),
                style = SemiBold12,
                color = KabTheme.colors.primaryOnCardText,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            )

            IconButton(
                onClick = onNext,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = android.R.drawable.arrow_down_float),
                    contentDescription = "forward",
                    tint = KabTheme.colors.primaryOnCardText,
                    modifier = Modifier
                        .size(12.dp)
                        .rotate(-90f)
                )
            }
        }
    }
}

/**
 * Форматирует текст для навигатора в зависимости от периода
 */
@Composable
private fun formatNavigatorLabel(chartData: ChartDashboardGraphicsData): String {
    return when (chartData.selectedPeriod) {
        ChartPeriod.WEEK -> {
            // Формат: "13 янв - 19 янв"
            val start = chartData.range.start.toDisplayedDate()
            val end = chartData.range.end.toDisplayedDate()
            "$start - $end"
        }
        ChartPeriod.MONTH -> {
            // Формат: "Январь 2026"
            val month = chartData.range.start.month.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            ).replaceFirstChar { it.uppercase() }
            val year = chartData.range.start.year
            "$month $year"
        }
        ChartPeriod.YEAR -> {
            // Формат: "2026"
            chartData.range.start.year.toString()
        }
    }
}