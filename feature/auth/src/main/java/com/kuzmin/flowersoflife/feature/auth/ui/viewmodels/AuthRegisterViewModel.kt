package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R.string.sign_up_title
import com.kuzmin.flowersoflife.common.R.string.unsuccessful_registration
import com.kuzmin.flowersoflife.common.R.string.user_not_initialized
import com.kuzmin.flowersoflife.common.R.string.user_registered
import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.domain.model.Family
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.SaveUserFamilyToLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.RegisterUserUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.SaveUserFamilyToRemoteUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.validators.RegisterUserContextualValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AuthRegisterViewModel(
    private val navigationManager: NavigationManager,
    private val resourceProvider: ResourceProvider,
    private val registerUserUseCase: RegisterUserUseCase,
    private val saveUserFamilyToLocalUseCase: SaveUserFamilyToLocalUseCase,
    private val saveUserFamilyToRemoteUseCase: SaveUserFamilyToRemoteUseCase,
    sharedFlowMap: SharedFlowMap<UiEvent>
) : AuthBaseViewModel(navigationManager, sharedFlowMap) {

    val userState: StateFlow<UserFamily?> = authState
        .map { (it as? AuthState.Success)?.userFamily }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), null
        )

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword: StateFlow<String> = _repeatPassword

    init {
        setAuthState(AuthState.Loading)
        updateAuthState {
            AuthState.Success(
                UserFamily(
                    User(
                        userId = "",
                        familyId = ""
                    ),
                    Family(
                        familyId = "",
                        familyName = "",
                        familyCode = "",
                    )
                )
            )
        }
    }

    suspend fun notifyTopBarDataChanged() {
        updateTopbarState(
            TopBarUiSettings(
                isHamburgerVisible = false,
                isBackVisible = true,
                title = resourceProvider.getString(sign_up_title)
            )
        )
    }

    fun updateRole(role: UserRole?) {
        val currentUserAndFamily = (authState.value as? AuthState.Success)?.userFamily ?: return
        val updated = currentUserAndFamily.copy(
            user = currentUserAndFamily.user.copy(
                role = role,
                isAdmin = if (role == UserRole.CHILD) false else currentUserAndFamily.user.isAdmin
            )
        )
        setAuthState(AuthState.Success(updated))
    }

    fun updateUserField(update: User.() -> User) {
        updateAuthState {
            val userAndFamily = (it as? AuthState.Success)?.userFamily ?: return@updateAuthState it
            AuthState.Success(
                userFamily = userAndFamily.copy(
                    user = userAndFamily.user.update()
                )
            )
        }
    }

    fun updateFamilyField(update: Family.() -> Family) {
        updateAuthState {
            val userFamily = (it as? AuthState.Success)?.userFamily ?: return@updateAuthState it
            AuthState.Success(
                userFamily = userFamily.copy(
                    family = userFamily.family.update()
                )
            )
        }
    }

    fun onRepeatPasswordChanged(value: String) {
        _repeatPassword.value = value
    }

    fun isPasswordMismatch(): Boolean {
        val userPassword =
            (authState.value as? AuthState.Success)?.userFamily?.user?.password.orEmpty()
        return _repeatPassword.value.isNotBlank() && _repeatPassword.value != userPassword
    }

    fun registerUser() {
        val userFamily = (authState.value as? AuthState.Success)?.userFamily
            ?: throw IllegalStateException(resourceProvider.getString(user_not_initialized))

        setAuthState(AuthState.Loading)

        viewModelScope.launch(ioCoroutineContext) {
            val errors =
                RegisterUserContextualValidator.validate(userFamily, repeatPassword.value)
            if (errors.isNotEmpty()) {
                setErrors(errors)
                setAuthState(AuthState.Success(userFamily))
                return@launch
            }

            val uid = registerUserUseCase(userFamily.user)

            if (uid != null) {
                val savedUser = saveUserFamilyToRemoteUseCase(
                    userFamily = userFamily
                )

                saveUserFamilyToLocalUseCase(
                    userFamily.copy(
                        user = userFamily.user.copy(userId = uid)
                    )
                )

                navigateToHome()
                showSnackMessage(
                    resourceProvider.getString(user_registered) + " ${savedUser?.userId}",
                    SnackbarMessageType.INFO
                )

                updateAuthState {
                    AuthState.Success(
                        userFamily.copy(
                            user = userFamily.user.copy(userId = uid),
                        )
                    )
                }
            } else {
                /*setAuthState(
                    AuthState.Error(
                        ServerRegisterException(
                            ServerRegisterErrorType.UNKNOWN_ERROR,
                            resourceProvider.getString(unsuccessful_registration)
                        )
                    )
                )*/
                showSnackMessage(
                    resourceProvider.getString(unsuccessful_registration),
                    SnackbarMessageType.ERROR
                )
                updateAuthState {
                    AuthState.Success(userFamily)
                }
            }
        }
    }

    fun cancelRegistration() {
        viewModelScope.launch {
            toStartAuth()
        }
    }
}