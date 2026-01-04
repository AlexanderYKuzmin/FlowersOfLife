package com.kuzmin.flowersoflife.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.R
import com.kuzmin.flowersoflife.common.model.TabBarUiSettings
import com.kuzmin.flowersoflife.core.domain.usecases.auth.CheckAuthUseCase
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.UI_EVENT
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarData
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFromFbUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.GetUserFromRemoteDbUseCase
import com.kuzmin.flowersoflife.ui.state.AppUiState
import com.kuzmin.flowersoflife.ui.state.MainScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val checkAuthUseCase: CheckAuthUseCase,
    private val getUserFromFbUseCase: GetUserFromFbUseCase,
    private val getUserFromDbUseCase: GetUserFromRemoteDbUseCase,
    private val resourceProvider: ResourceProvider,
    private val sharedFlowMap: SharedFlowMap<UiEvent>,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            sharedFlowMap.emit(
                UI_EVENT,
                UiEvent.ShowSnackbar(
                    throwable.localizedMessage ?: resourceProvider.getString(R.string.unknown_error),
                    SnackbarMessageType.ERROR
                )
            )
        }
    }

    val ioContext = Dispatchers.IO + coroutineExceptionHandler

    private val _screenState = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    private val _appState = MutableStateFlow<AppUiState>(AppUiState.Loading)
    val appState = _appState.asStateFlow()

    private val _snackbarState = MutableSharedFlow<SnackbarData>()
    val snackbarState = _snackbarState.asSharedFlow()

    init {
        observeUiEvents()
        viewModelScope.launch(ioContext) {
            val userFb = getUserFromFbUseCase()

            val user = if (userFb != null) {
                getUserFromDbUseCase(userFb.userId)
            } else {
                null
            }

            delay(3000)
            _screenState.update {
                MainScreenState.SuccessEmpty
            }

            Log.d("CAB-13", "init main screen view model USER = $user")
            _appState.value = AppUiState.Success(
                user = user,
                isAuthorized = userFb != null,
                tabbarUiSettings = TabBarUiSettings()
            )
        }
    }

    private fun observeUiEvents() {
        viewModelScope.launch {
            sharedFlowMap.observe(UI_EVENT).collect { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        _snackbarState.emit(
                            SnackbarData(
                                message = event.message,
                                type = event.type
                            )
                        )
                    }

                    is UiEvent.UpdateAppState -> {
                        val state = appState.value as? AppUiState.Success ?: return@collect
                        _appState.update {
                            state.copy(tabbarUiSettings = event.tabBarUiSettings)
                        }
                    }

                    else -> Unit
                }
            }
        }
    }


    /*private suspend fun observeAppStateChangeData() {
        userSharedFlowMap.observe(AUTHORIZED).collectLatest { user ->
            val state = _appState.value as? AppUiState.Success ?: return@collectLatest
            _appState.update { state.copy(user = user) }
        }

        topbarSharedFlowMap.observe(TOPBAR_STATE).collectLatest { topbarState ->
            if (topbarState == null) throw IllegalStateException("Topbar state is null")

            val state = _appState.value as? AppUiState.Success ?: return@collectLatest
            _appState.update {
                with(topbarState) {
                    state.copy(
                        title = title,
                        isHamburgerVisible = isHamburgerVisible,
                        isBackVisible = isBackVisible
                    )
                }
            }
        }
    }*/

    fun checkAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            _appState.value = AppUiState.Loading

            val isAuthorized = checkAuthUseCase()
        }
    }

    fun getNavigationManager(): NavigationManager = navigationManager
}