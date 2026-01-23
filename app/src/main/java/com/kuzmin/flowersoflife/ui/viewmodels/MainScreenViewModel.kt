package com.kuzmin.flowersoflife.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.common.model.TopBarUiSettings
import com.kuzmin.flowersoflife.core.domain.usecases.auth.CheckAuthUseCase
import com.kuzmin.flowersoflife.core.local.event_bus.FlowKey.UI_EVENT
import com.kuzmin.flowersoflife.core.local.event_bus.SharedFlowMap
import com.kuzmin.flowersoflife.core.local.resource_provider.ResourceProvider
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarData
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.event.UiEvent
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.SaveUserFamilyToLocalUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetUserFamilyFromRemoteUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.GetUserFromFbUseCase
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
    private val getUserFamilyFromRemoteUseCase: GetUserFamilyFromRemoteUseCase,
    private val saveUserFamilyToLocalUseCase: SaveUserFamilyToLocalUseCase,
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

            val userFamily = if (userFb != null) {
                getUserFamilyFromRemoteUseCase(userFb.userId)
            } else {
                null
            }

            userFamily?.let {
                launch { saveUserFamilyToLocalUseCase(it) }
            }

            delay(3000)
            _screenState.update {
                MainScreenState.SuccessEmpty
            }

            _appState.value = AppUiState.Success(
                userFamily = userFamily,
                isAuthorized = userFamily != null,
                topBarUiSettings = TopBarUiSettings(
                    title = resourceProvider.getString(R.string.app_name),
                    isHamburgerVisible = true,
                    isBackVisible = false
                )
            )
        }
    }

    fun notifyTopbarStateChange() {
        val state = appState.value as? AppUiState.Success ?: return
        _appState.value = state.copy(
            topBarUiSettings = TopBarUiSettings(
                title = resourceProvider.getString(R.string.app_name),
            )
        )
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
                            state.copy(
                                topBarUiSettings = event.topBarUiSettings,
                                isBottomNavVisible = event.isBottomNavVisible
                            )
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