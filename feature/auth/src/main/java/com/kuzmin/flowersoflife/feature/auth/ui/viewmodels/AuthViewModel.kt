package com.kuzmin.flowersoflife.feature.auth.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.usecases.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.core.domain.usecases.RegisterUserUseCase
import com.kuzmin.flowersoflife.core.domain.usecases.SignInUseCase
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val getUserFromLocalStorageUseCase: GetUserFromLocalStorageUseCase
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

    fun registerUser(user: User) {
        viewModelScope.launch(ioContext) {
            val registeredUser = registerUserUseCase(user)
            _authState.value =
                if (registeredUser != null) AuthState.Success(registeredUser)
                else AuthState.Error(Exception())
        }
    }

    fun signInUser(user: User) {
        if (user.isUserConsistent) {
            viewModelScope.launch(ioContext) {
                val isUserAuthorized = signInUseCase(user)
                _authState.value =
                    if (isUserAuthorized) AuthState.Success(user)
                    else AuthState.Error(Exception()) //придумать обработку ошибки
            }
        } else {
            _authState.value = AuthState.Error(Exception()) // Что-то написать про не хорошо сделанного юзера
        }

    }

    fun onUserDataChanged(user: User) {
        _authState.value = AuthState.Success(user)
    }
}