package com.kuzmin.flowersoflife.common.ext

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.toDisplayedDate(): String {
    return format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    )
}

fun LocalDate.toDisplayedShortDate(): String {
    return format(
        DateTimeFormatter.ofPattern("dd.MM")
    )
}

fun LocalDate.toMonthName(): String {
    return format(DateTimeFormatter.ofPattern("LLLL", Locale.getDefault()))
}

fun YearMonth.toMonthName(): String {
    return format(
        DateTimeFormatter.ofPattern("LLLL", Locale.getDefault())
    )
}