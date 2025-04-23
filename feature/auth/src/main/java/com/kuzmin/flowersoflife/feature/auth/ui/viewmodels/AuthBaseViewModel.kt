package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.kuzmin.flowersoflife.common.constants.Destination
import com.kuzmin.flowersoflife.common.constants.Route
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class AuthBaseViewModel(
    private val navigationManager: NavigationManager
) : ViewModel() {

    open val errors = mutableMapOf<RegisterErrorType, String>()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    open val authState: StateFlow<AuthState> get() = _authState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _authState.value = AuthState.Error(throwable)
    }

    val ioCoroutineContext = Dispatchers.IO + coroutineExceptionHandler

    protected fun setAuthState(newState: AuthState) {
        _authState.value = newState
    }

    protected fun updateAuthState(block: (AuthState) -> AuthState) {
        _authState.value = block(_authState.value)
    }

    fun toStartAuth() {
        navigationManager.popUpTo(Destination.AUTH_LOGIN)
    }

    fun navigateToHome() {
        if (authState.value is AuthState.Success) {
            val user = (authState.value as? AuthState.Success)?.user ?: return
            val route = if (user.role == UserRole.PARENT) {
                Route.PARENT_NAV_GRAPH
            } else {
                Route.CHILD_NAV_GRAPH
            }
            navigationManager.navigate(route) {
                popUpTo(Destination.AUTH_LOGIN)
            }
        }
    }
}