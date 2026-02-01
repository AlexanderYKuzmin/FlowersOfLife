package com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.contract

import com.kuzmin.flowersoflife.feature.home.ui.model.ChartPeriod

interface OnChartAction {
    fun onChartPeriodChange(period: ChartPeriod)
    fun onChartNavigateNext()
    fun onChartNavigatePrevious()
}