package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.constants.Destination
import com.kuzmin.flowersoflife.common.constants.Route
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.usecases.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.feature.auth.api.usecases.RegisterUserUseCase
import com.kuzmin.flowersoflife.feature.auth.api.usecases.SignInUseCase
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthLoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getUserFromLocalStorageUseCase: GetUserFromLocalStorageUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _authState.value = AuthState.Error(throwable)
    }

    private val ioContext = Dispatchers.IO + coroutineExceptionHandler

    init {
        _authState.value = AuthState.Loading
        viewModelScope.launch(ioContext) {
            val user = getUserFromLocalStorageUseCase()
            _authState.value = AuthState.Success(user)
        }
    }

    fun navigateToRegisterUser() {
        navigationManager.navigate(Destination.AUTH_REGISTER)
    }

    fun signInUser(credentials: AuthCredentials, rememberMe: Boolean) {
        viewModelScope.launch(ioContext) {
            val isUserAuthorized = signInUseCase(credentials)
            if (isUserAuthorized) {
                val route = when(
                    if (authState.value is AuthState.Success) (authState.value as AuthState.Success).user.role else null) {
                    UserRole.PARENT -> Route.PARENT_NAV_GRAPH
                    UserRole.CHILD -> Route.CHILD_NAV_GRAPH
                    else -> "" //TODO обработать исключение
                }

                navigationManager.navigate(route) {
                    popUpTo(Route.AUTH_NAV_GRAPH) {
                        inclusive = true
                    }
                }
            }

            else AuthState.Error(Exception("Неверный логин или пароль")) //TODO придумать обработку ошибки/ snackbar запустить
        }
    }

    fun cancelAuth() {
        navigationManager.popBackStack()
    }
}