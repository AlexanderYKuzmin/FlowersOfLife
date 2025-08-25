package com.kuzmin.flowersoflife.core.local.data_listeners

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedFlowMapImpl<K, V> @Inject constructor() : SharedFlowMap<K, V> {

    private val flows = ConcurrentHashMap<K, MutableSharedFlow<V?>>()

    override fun emit(key: K, value: V) {
        val flow = flows.getOrPut(key) { MutableSharedFlow(replay = 1) }
        flow.tryEmit(value)
    }

    override fun observe(key: K): Flow<V?> {
        return flows.getOrPut(key) { MutableSharedFlow(replay = 1) }.asSharedFlow()
    }

    override fun remove(key: K) {
        flows.remove(key)
    }

    override fun clear() {
        flows.clear()
    }
}
