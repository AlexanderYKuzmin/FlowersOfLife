package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R.string.sign_in_title
import com.kuzmin.flowersoflife.common.model.AppUiData
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.usecases.auth.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.core.ui.event.UiEventFlow
import com.kuzmin.flowersoflife.feature.auth.api.usecases.SignInUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalLoginException
import com.kuzmin.flowersoflife.feature.auth.validators.CredentialsValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class AuthLoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val getUserFromLocalStorageUseCase: GetUserFromLocalStorageUseCase,
    private val navigationManager: NavigationManager,
    private val resourceProvider: ResourceProvider,
    uiEventFlow: UiEventFlow
) : AuthBaseViewModel(navigationManager, uiEventFlow) {

    init {
        setAuthState(AuthState.Loading)
        viewModelScope.launch(ioCoroutineContext) {
            updateTopbarState(
                AppUiData(
                    isHamburgerVisible = false,
                    isBackVisible = false,
                    title = resourceProvider.getString(sign_in_title)
                )
            )

            val user = getUserFromLocalStorageUseCase()
            withContext(Dispatchers.Main) {
                setAuthState(AuthState.Success(user))
            }
        }
    }

    fun navigateToRegisterUser() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationCommand.ToDestination(Destination.AUTH_REGISTER))
        }
    }

    fun signInUser(credentials: AuthCredentials, rememberMe: Boolean) {
        viewModelScope.launch(ioCoroutineContext) {
            val errors = CredentialsValidator.validate(credentials)
            setErrors(errors)

            if (errors.isNotEmpty()) return@launch

            val isUserAuthorized = signInUseCase(credentials)

            val user = (authState.value as? AuthState.Success)?.user
            if (isUserAuthorized && user != null) {
                updateAppUser(user = user)
                navigateToHome()
            }
            else throw IllegalLoginException()
        }
    }

    fun refresh() {
        setAuthState(AuthState.Success(User()))
    }

    fun cancelAuth() {
        viewModelScope.launch {
            toStartAuth()
        }
    }

    private fun updateAppUser(user: User) {

    }
}