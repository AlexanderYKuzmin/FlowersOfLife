package com.kuzmin.flowersoflife.feature.home.domain.event

sealed class ChildEvent {
    data object Created : ChildEvent()
    data object Updated : ChildEvent()
    data object Deleted : ChildEvent()
    data class PendingRemoval(val isApproved: Boolean) : ChildEvent()
}