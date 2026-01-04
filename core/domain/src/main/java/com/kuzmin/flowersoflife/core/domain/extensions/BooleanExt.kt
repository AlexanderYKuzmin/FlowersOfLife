package com.kuzmin.flowersoflife.core.domain.extensions

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}