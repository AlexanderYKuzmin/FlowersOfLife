package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R.string.sign_in_title
import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
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
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetUserFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.SaveUserFamilyToLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetCurrentFbUserUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetUserFamilyFromRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.SignInUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalLoginException
import com.kuzmin.flowersoflife.feature.auth.validators.CredentialsValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class AuthLoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val getUserFamilyFromLocalUseCase: GetUserFamilyFromLocalUseCase,
    private val getUserFamilyFromRemoteUseCase: GetUserFamilyFromRemoteUseCase,
    private val getCurrentFbUserUseCase: GetCurrentFbUserUseCase,
    private val saveUserFamilyToLocalUseCase: SaveUserFamilyToLocalUseCase,
    private val navigationManager: NavigationManager,
    private val resourceProvider: ResourceProvider,
    sharedFlowMap: SharedFlowMap<UiEvent>
) : AuthBaseViewModel(navigationManager, sharedFlowMap) {

    init {
        setAuthState(AuthState.Loading)
        viewModelScope.launch(ioCoroutineContext) {
            val userAndFamily = getUserFamilyFromLocalUseCase()

            Log.d("Auth", "Auth. User and family: $userAndFamily")
            notifyAppStateChanged()
            withContext(Dispatchers.Main) {
                setAuthState(
                    AuthState.Success(
                        userAndFamily
                    )
                )
            }
        }
    }

    suspend fun notifyAppStateChanged() {
        updateAppState(
            topBarUiSettings = TopBarUiSettings(
                isHamburgerVisible = false,
                isBackVisible = false,
                title = resourceProvider.getString(sign_in_title)
            ),
            isBottomNavVisible = false
        )
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

            val userFamily: UserFamily? = if (authState.value is AuthState.Success && isUserAuthorized) {
                (authState.value as AuthState.Success).userFamily
                    .takeIf { it.user.isConsistent }
                    ?: getUserFamilyFromRemote()
                        ?.let {
                            saveUserFamilyToLocalUseCase(it)
                            it
                        }
            } else null

            Log.d("Auth", "Auth. UserFamily: $userFamily, isUserAuthorized: $isUserAuthorized")
            if (isUserAuthorized && userFamily != null) {
                setAuthState(AuthState.Success(userFamily))
                updateAppUser(user = userFamily.user)
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

    private suspend fun getUserFamilyFromRemote(): UserFamily? {
        val currentUserId = getCurrentFbUserUseCase()?.uid

        return currentUserId?.let {
            getUserFamilyFromRemoteUseCase(it)
        }
    }
}