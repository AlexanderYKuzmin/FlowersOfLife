package com.kuzmin.flowersoflife.core.local.resource_provider

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String
}