package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R.string.sign_up_title
import com.kuzmin.flowersoflife.common.R.string.unsuccessful_registration
import com.kuzmin.flowersoflife.common.R.string.user_not_initialized
import com.kuzmin.flowersoflife.common.R.string.user_registered
import com.kuzmin.flowersoflife.common.model.TabBarUiSettings
import com.kuzmin.flowersoflife.core.domain.model.Family
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.user.RegisterUserUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.SaveUserFamilyToLocalUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.ServerRegisterException
import com.kuzmin.flowersoflife.feature.auth.exception.errors.ServerRegisterErrorType
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
    sharedFlowMap: SharedFlowMap<UiEvent>
) : AuthBaseViewModel(navigationManager, sharedFlowMap) {

    val userState: StateFlow<UserFamily?> = authState
        .map { (it as? AuthState.Success)?.userFamily }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

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
        viewModelScope.launch {
            updateTopbarState(
                TabBarUiSettings(
                    isHamburgerVisible = false,
                    isBackVisible = true,
                    title = resourceProvider.getString(sign_up_title)
                )
            )
        }
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

    fun updateIsAdmin(isAdmin: Boolean) {
        val userAndFamily = (authState.value as? AuthState.Success)?.userFamily ?: return
        if (userAndFamily.user.role == UserRole.PARENT) {
            setAuthState(
                AuthState.Success(
                    userAndFamily.copy(
                        user = userAndFamily.user.copy(isAdmin = isAdmin)
                    )
                )
            )
        }
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
            val userAndFamily = (it as? AuthState.Success)?.userFamily ?: return@updateAuthState it
            AuthState.Success(
                userFamily = userAndFamily.copy(
                    family = userAndFamily.family.update()
                )
            )
        }
    }

    fun onRepeatPasswordChanged(value: String) {
        _repeatPassword.value = value
    }

    fun isPasswordMismatch(): Boolean {
        val userPassword = (authState.value as? AuthState.Success)?.userFamily?.user?.password.orEmpty()
        return _repeatPassword.value.isNotBlank() && _repeatPassword.value != userPassword
    }

    fun registerUser() {
        val userAndFamily = (authState.value as? AuthState.Success)?.userFamily
            ?: throw IllegalStateException(resourceProvider.getString(user_not_initialized))

        setAuthState(AuthState.Loading)

        viewModelScope.launch(ioCoroutineContext) {
            val errors = RegisterUserContextualValidator.validate(userAndFamily, repeatPassword.value)
            if (errors.isNotEmpty()) {
                setErrors(errors)
                return@launch
            }

            val uid = registerUserUseCase(userAndFamily.user)

            if (uid != null) {
                //setAuthState(AuthState.Success(registeredUser))
                //TODO Созранить пользователя и данные по семье в базе данных. Сделать useCase для сохранения. Создать новый репозиторий UserRepository

                saveUserFamilyToLocalUseCase(
                    userAndFamily.copy(
                        user = userAndFamily.user.copy(userId = uid)
                    )
                )

                navigateToHome()
                showSnackMessage(
                    resourceProvider.getString(user_registered),
                    SnackbarMessageType.INFO
                )
            } else {
                setAuthState(
                    AuthState.Error(
                        ServerRegisterException(
                            ServerRegisterErrorType.UNKNOWN_ERROR,
                            resourceProvider.getString(unsuccessful_registration)
                        )
                    )
                )
            }
        }
    }

    fun cancelRegistration() {
        viewModelScope.launch {
            toStartAuth()
        }
    }
}