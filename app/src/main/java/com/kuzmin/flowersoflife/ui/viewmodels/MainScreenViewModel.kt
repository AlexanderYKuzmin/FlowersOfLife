package com.kuzmin.flowersoflife.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.R
import com.kuzmin.flowersoflife.core.domain.usecases.CheckAuthUseCase
import com.kuzmin.flowersoflife.core.domain.usecases.GetUserFromLocalStorageUseCase
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarData
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.ui.state.AppUiState
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.core.ui.event.UiEventFlow
import com.kuzmin.flowersoflife.extensions.updateWith
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
    private val resourceProvider: ResourceProvider,
    private val uiEventFlow: UiEventFlow
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            uiEventFlow.emit(
                UiEvent.ShowSnackbar(
                    throwable.localizedMessage ?: resourceProvider.getString(R.string.unknown_error),
                    SnackbarMessageType.ERROR
                )
            )
        }
    }

    private val _appState = MutableStateFlow<AppUiState>(AppUiState.Loading)
    val appState = _appState.asStateFlow()

    private val _snackbarState = MutableSharedFlow<SnackbarData>()
    val snackbarState = _snackbarState.asSharedFlow()

    init {
        observeUiEvents()

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val user = getUserFromLocalStorageUseCase()
            val isAuthorized = if (user.isUserConsistent) {
                false
            } else {
                checkAuthUseCase()
            }

            delay(5000)
            _appState.update {
                AppUiState.Success(user = user, isAuthorized = isAuthorized)
            }
        }
    }

    private fun observeUiEvents() {
        viewModelScope.launch {
            uiEventFlow.events.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        Log.d("CAB-8", "observeUiEvents: $event")
                        _snackbarState.emit(
                            SnackbarData(
                                message = event.message,
                                type = event.type
                            )
                        )
                    }

                    is UiEvent.UpdateAppState -> {
                        _appState.update { currentState ->
                            if (currentState is AppUiState.Success) {
                                currentState.updateWith(event.appData, resourceProvider)
                            } else currentState
                        }
                    }

                    else -> Unit
                }
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