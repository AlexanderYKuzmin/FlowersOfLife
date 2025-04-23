package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R.string.unsuccessful_registration
import com.kuzmin.flowersoflife.common.R.string.user_not_initialized
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.feature.auth.R
import com.kuzmin.flowersoflife.feature.auth.api.usecases.RegisterUserUseCase
import com.kuzmin.flowersoflife.feature.auth.api.usecases.SaveUserDatastoreUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.domain.usecases.RegisterUserUseCaseImpl
import com.kuzmin.flowersoflife.feature.auth.exception.AuthRegisterException
import com.kuzmin.flowersoflife.feature.auth.exception.ServerRegisterException
import com.kuzmin.flowersoflife.feature.auth.exception.errors.ServerRegisterErrorType
import com.kuzmin.flowersoflife.feature.auth.validators.RegisterUserValidator
import dagger.hilt.android.lifecycle.HiltViewModel
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

    //TODO Разобраться с пароль повторно. Надо проверять не отходя от кассы

    fun registerUser() {
        viewModelScope.launch(ioCoroutineContext) {
            val user = (authState.value as? AuthState.Success)?.user
                ?: throw IllegalStateException(resourceProvider.getString(user_not_initialized))

            val errors = RegisterUserValidator.validate(user)
            if (errors.isNotEmpty()) {
                setAuthState(AuthState.Error(AuthRegisterException(errors)))
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
}