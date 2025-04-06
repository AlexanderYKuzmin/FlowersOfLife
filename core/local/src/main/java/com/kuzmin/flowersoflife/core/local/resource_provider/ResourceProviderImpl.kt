package com.kuzmin.flowersoflife.core.local.resource_provider

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceProvider {
    override fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }
}