package com.kuzmin.flowersoflife.feature.auth.domain.model

import com.kuzmin.flowersoflife.core.domain.model.User

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class Error(val throwable: Throwable) : AuthState()
    data class Success(val user: User) : AuthState()
}