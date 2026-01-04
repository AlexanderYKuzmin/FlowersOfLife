package com.kuzmin.flowersoflife.core.local.event_bus

import kotlinx.coroutines.flow.Flow

interface SharedFlowMap<V> {
    fun emit(key: String, value: V)
    fun observe(key: String): Flow<V?>
    fun remove(key: String)
    fun clear()
}