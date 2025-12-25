package com.kuzmin.flowersoflife.core.local.data_listeners

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedFlowMapImpl<V> @Inject constructor() : SharedFlowMap<String, V> {

    private val flows = ConcurrentHashMap<String, MutableSharedFlow<V?>>()

    override fun emit(key: String, value: V) {
        val flow = flows.getOrPut(key) { MutableSharedFlow(replay = 1) }
        flow.tryEmit(value)
    }

    override fun observe(key: String): Flow<V?> {
        return flows.getOrPut(key) { MutableSharedFlow(replay = 1) }.asSharedFlow()
    }

    override fun remove(key: String) {
        flows.remove(key)
    }

    override fun clear() {
        flows.clear()
    }
}
