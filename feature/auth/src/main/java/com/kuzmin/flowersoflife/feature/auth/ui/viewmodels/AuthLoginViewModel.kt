package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R.string.sign_in_title
import com.kuzmin.flowersoflife.common.model.TabBarUiSettings
import com.kuzmin.flowersoflife.core.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.core.domain.model.Family
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFromLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.SignInUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalLoginException
import com.kuzmin.flowersoflife.feature.auth.validators.CredentialsValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class AuthLoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase,
    private val getUserFamilyFromLocalUseCase: GetUserFamilyFromLocalUseCase,
    private val navigationManager: NavigationManager,
    private val resourceProvider: ResourceProvider,
    sharedFlowMap: SharedFlowMap<UiEvent>
) : AuthBaseViewModel(navigationManager, sharedFlowMap) {

    init {
        setAuthState(AuthState.Loading)
        viewModelScope.launch(ioCoroutineContext) {
            updateTopbarState(
                TabBarUiSettings(
                    isHamburgerVisible = false,
                    isBackVisible = false,
                    title = resourceProvider.getString(sign_in_title)
                )
            )

            val userAndFamily = getUserFamilyFromLocalUseCase()

            withContext(Dispatchers.Main) {
                setAuthState(
                    AuthState.Success(
                        userAndFamily
                    )
                )
            }
        }
    }

    fun navigateToRegisterUser() {
        viewModelScope.launch {
            navigationManager.navigate(NavigationCommand.ToDestination(Destination.AUTH_REGISTER))
        }
    }

    fun signInUser(credentials: AuthCredentials) {
        viewModelScope.launch(ioCoroutineContext) {
            val errors = CredentialsValidator.validate(credentials)
            setErrors(errors)

            if (errors.isNotEmpty()) return@launch

            val isUserAuthorized = signInUseCase(credentials)

            val user = (authState.value as? AuthState.Success)?.userFamily?.user
            if (isUserAuthorized && user != null) {
                updateAppUser(user = user)
                navigateToHome()
            }
            else throw IllegalLoginException()
        }
    }

    fun refresh() {
        setAuthState(
            AuthState.Success(
                UserFamily(
                    User(
                        userId = "",
                        familyId = ""
                    ),
                    Family(
                        familyId = "",
                        familyName = "",
                        familyCode = ""
                    )
                )
            )
        )
    }

    fun cancelAuth() {
        viewModelScope.launch {
            toStartAuth()
        }
    }

    private fun updateAppUser(user: User) {

    }
}