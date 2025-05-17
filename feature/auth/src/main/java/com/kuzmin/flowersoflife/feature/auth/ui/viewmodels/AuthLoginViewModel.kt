package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.constants.Destination
import com.kuzmin.flowersoflife.common.constants.Route
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.usecases.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.ui.event.UiEventFlow
import com.kuzmin.flowersoflife.feature.auth.api.usecases.SignInUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalLoginException
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalRouteException
import com.kuzmin.flowersoflife.feature.auth.validators.CredentialsValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthLoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val getUserFromLocalStorageUseCase: GetUserFromLocalStorageUseCase,
    private val navigationManager: NavigationManager,
    uiEventFlow: UiEventFlow
) : AuthBaseViewModel(navigationManager, uiEventFlow) {

    init {
        setAuthState(AuthState.Loading)
        viewModelScope.launch(ioCoroutineContext) {
            val user = getUserFromLocalStorageUseCase()
            setAuthState(AuthState.Success(user))
        }
    }

    fun navigateToRegisterUser() {
        navigationManager.navigate(Destination.AUTH_REGISTER)
    }

    fun signInUser(credentials: AuthCredentials, rememberMe: Boolean) {
        viewModelScope.launch(ioCoroutineContext) {
            val errors = CredentialsValidator.validate(credentials)
            setErrors(errors)

            if (errors.isNotEmpty()) return@launch

            val isUserAuthorized = signInUseCase(credentials)
            if (isUserAuthorized) {
                val route = when(
                    if (authState.value is AuthState.Success) (authState.value as AuthState.Success).user.role else null) {
                    UserRole.PARENT -> Route.PARENT_NAV_GRAPH
                    UserRole.CHILD -> Route.CHILD_NAV_GRAPH
                    else -> throw IllegalRouteException()
                }

                navigationManager.navigate(route) {
                    popUpTo(Route.AUTH_NAV_GRAPH) {
                        inclusive = true
                    }
                }
            }

            else throw IllegalLoginException()
        }
    }

    fun refresh() {
        setAuthState(AuthState.Success(User()))
    }

    fun cancelAuth() {
        toStartAuth()
    }
}