package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.model.AppUiData
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.Route
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.core.ui.event.UiEventFlow
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalRouteException
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class AuthBaseViewModel(
    private val navigationManager: NavigationManager,
    private val uiEventFlow: UiEventFlow
) : ViewModel() {

    private val _fieldErrors = MutableStateFlow<Set<RegisterErrorType>>(emptySet())
    val fieldErrors: StateFlow<Set<RegisterErrorType>> = _fieldErrors

    protected fun setErrors(newErrors: Set<RegisterErrorType>) {
        _fieldErrors.value = newErrors
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    open val authState: StateFlow<AuthState> get() = _authState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _authState.value = AuthState.Error(throwable)
    }

    val ioCoroutineContext = Dispatchers.IO + coroutineExceptionHandler

    protected fun setAuthState(newState: AuthState) {
        _authState.value = newState
    }

    protected fun updateAuthState(block: (AuthState) -> AuthState) {
        _authState.value = block(_authState.value)
    }

    protected suspend fun updateTopbarState(appUiData: AppUiData) {
        uiEventFlow.emit(
            UiEvent.UpdateAppState(
                appUiData = appUiData
            )
        )
    }

    suspend fun toStartAuth() {
        navigationManager.navigate(
            NavigationCommand.Back()
        )
    }

    suspend fun navigateToHome() {
        val currentState = authState.value
        if (currentState !is AuthState.Success) return

        val user = currentState.user

        val route = when (user.role) {
            UserRole.PARENT -> Route.PARENT_NAV_GRAPH
            UserRole.CHILD -> Route.CHILD_NAV_GRAPH
            else -> throw IllegalRouteException()
        }

        navigationManager.navigate(NavigationCommand.ToGraph(route, Route.AUTH_NAV_GRAPH))
    }


    fun showSnackMessage(message: String, type: SnackbarMessageType) {
        viewModelScope.launch {
            uiEventFlow.emit(UiEvent.ShowSnackbar(message, type))
        }
    }
}