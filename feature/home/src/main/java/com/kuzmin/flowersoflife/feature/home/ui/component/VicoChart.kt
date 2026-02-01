package com.kuzmin.flowersoflife.feature.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.Regular16
import com.kuzmin.flowersoflife.feature.home.ui.model.ChartDashboardGraphicsData
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.kuzmin.flowersoflife.core.ui.R as CoreUiRes

@Composable
fun VicoChart(
    chartData: ChartDashboardGraphicsData,
    modifier: Modifier = Modifier
) {
    if (chartData.data.isEmpty()) {
        EmptyChartPlaceholder(modifier = modifier)
        return
    }

    val modelProducer = remember { CartesianChartModelProducer() }

    val successColor = KabTheme.colors.successText
    val errorColor = KabTheme.colors.errorText

    LaunchedEffect(chartData.data) {
        modelProducer.runTransaction {
            columnSeries {
                series(chartData.data.map { it.coins })
            }
        }
    }

    val columnProvider = remember(chartData.data, successColor, errorColor) {
        object : ColumnCartesianLayer.ColumnProvider {
            override fun getColumn(
                entry: ColumnCartesianLayerModel.Entry,
                seriesIndex: Int,
                extraStore: ExtraStore
            ): LineComponent {
                val value = chartData.data.getOrNull(entry.x.toInt())?.coins ?: 0
                val color = if (value >= 0) {
                    successColor
                } else {
                    errorColor
                }
                return LineComponent(
                    fill = Fill(color.toArgb()),
                    strokeFill = Fill.Transparent,
                    thicknessDp = 6f,
                )
            }

            override fun getWidestSeriesColumn(
                seriesIndex: Int,
                extraStore: ExtraStore
            ): LineComponent {
                return LineComponent(
                    fill = Fill.Black,
                    strokeFill = Fill.Transparent,
                    thicknessDp = 6f,
                )
            }
        }
    }

    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider = columnProvider,
                columnCollectionSpacing = 4.dp
            ),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom (
                valueFormatter = { _, value, _ ->
                    chartData.data.getOrNull(value.toInt())?.label ?: " "
                }
            )
        ),
        modelProducer = modelProducer,
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
    )

}

@Composable
private fun EmptyChartPlaceholder(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = CoreUiRes.drawable.baseline_error_outline_24),
                contentDescription = null,
                tint = KabTheme.colors.frameInactive,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = stringResource(R.string.chart_no_data),
                style = Regular16,
                color = KabTheme.colors.frameInactive
            )
        }
    }
}