package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R.string.unsuccessful_registration
import com.kuzmin.flowersoflife.common.R.string.user_not_initialized
import com.kuzmin.flowersoflife.common.constants.Destination
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.feature.auth.api.usecases.RegisterUserUseCase
import com.kuzmin.flowersoflife.feature.auth.api.usecases.SaveUserDatastoreUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.ServerRegisterException
import com.kuzmin.flowersoflife.feature.auth.exception.errors.ServerRegisterErrorType
import com.kuzmin.flowersoflife.feature.auth.validators.RegisterUserValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModel @Inject constructor(
    private val navigationManager: NavigationManager,
    private val resourceProvider: ResourceProvider,
    private val registerUserUseCase: RegisterUserUseCase,
    private val saveUserDatastoreUseCase: SaveUserDatastoreUseCase
) : AuthBaseViewModel(navigationManager) {

    val userState: StateFlow<User?> = authState
        .map { (it as? AuthState.Success)?.user }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword: StateFlow<String> = _repeatPassword

    init {
        updateAuthState {
            AuthState.Success(User())
        }
    }

    fun updateRole(role: UserRole?) {
        val currentUser = (authState.value as? AuthState.Success)?.user ?: User()
        val updatedUser = currentUser.copy(
            role = role,
            isAdmin = if (role == UserRole.CHILD) false else currentUser.isAdmin
        )
        setAuthState(AuthState.Success(updatedUser))
    }

    fun updateIsAdmin(isAdmin: Boolean) {
        val currentUser = (authState.value as? AuthState.Success)?.user ?: return
        if (currentUser.role == UserRole.PARENT) {
            setAuthState(AuthState.Success(currentUser.copy(isAdmin = isAdmin)))
        }
    }

    fun updateUserField(update: User.() -> User) {
        updateAuthState {
            val user = (it as? AuthState.Success)?.user ?: return@updateAuthState it
            AuthState.Success(user.update())
        }
    }

    fun onRepeatPasswordChanged(value: String) {
        _repeatPassword.value = value
    }

    fun isPasswordMismatch(): Boolean {
        val userPassword = (authState.value as? AuthState.Success)?.user?.password.orEmpty()
        return _repeatPassword.value.isNotBlank() && _repeatPassword.value != userPassword
    }

    fun registerUser() {
        viewModelScope.launch(ioCoroutineContext) {
            val user = (authState.value as? AuthState.Success)?.user
                ?: throw IllegalStateException(resourceProvider.getString(user_not_initialized))

            val errors = RegisterUserValidator.validate(user, repeatPassword.value)
            if (errors.isNotEmpty()) {
                setErrors(errors)
                return@launch
            }

            val registeredUser = registerUserUseCase.invoke(user)
            if (registeredUser != null) {
                saveUserDatastoreUseCase.invoke(registeredUser)
                navigateToHome()
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
        navigationManager.popUpTo(Destination.AUTH_LOGIN)
    }
}