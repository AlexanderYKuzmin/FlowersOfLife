package com.kuzmin.flowersoflife.core.local.event_bus

import kotlinx.coroutines.flow.Flow

interface SharedFlowMap<K, V> {
    fun emit(key: K, value: V)
    fun observe(key: K): Flow<V?>
    fun remove(key: K)
    fun clear()
}