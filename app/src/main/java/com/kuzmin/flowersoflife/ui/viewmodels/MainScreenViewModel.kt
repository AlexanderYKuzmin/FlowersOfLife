package com.kuzmin.flowersoflife.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.R
import com.kuzmin.flowersoflife.core.domain.usecases.CheckAuthUseCase
import com.kuzmin.flowersoflife.core.domain.usecases.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.domain.model.AppUiState
import com.kuzmin.flowersoflife.domain.model.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val checkAuthUseCase: CheckAuthUseCase,
    private val getUserFromLocalStorageUseCase: GetUserFromLocalStorageUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _uiEvent.emit(
                UiEvent.ShowSnackbar(
                    throwable.localizedMessage ?: resourceProvider.getString(R.string.unknown_error)
                )
            )
        }
    }

    private val _appState = MutableStateFlow<AppUiState>(AppUiState.Loading)
    val appState = _appState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val user = getUserFromLocalStorageUseCase()
            val isAuthorized = if (user.isUserConsistent) {
                false
            } else {
                checkAuthUseCase()
            }

            Log.d("MainScreenViewModel", "user: $user, isAuthorized: $isAuthorized")
            delay(5000)
            _appState.update {
                AppUiState.Success(user = user, isAuthorized = isAuthorized)
            }
        }
    }

    fun checkAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            _appState.value = AppUiState.Loading

            val isAuthorized = checkAuthUseCase()
        }
    }
}