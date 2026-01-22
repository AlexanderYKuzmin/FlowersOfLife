package com.kuzmin.flowersoflife.feature.home.ui.screen.children

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.feature.api.usecases.home.GetChildrenDashboardUseCase
import com.kuzmin.flowersoflife.feature.api.usecases.user.local.GetFamilyFromLocalUseCase
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.ChildrenListState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChildrenListViewModel(
    private val getFamilyFromLocalUseCase: GetFamilyFromLocalUseCase,
    private val getChildrenDashboardUseCase: GetChildrenDashboardUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _state = MutableStateFlow<ChildrenListState>(ChildrenListState.Loading)
    val state = _state.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }

    private val ioContext = Dispatchers.IO + coroutineExceptionHandler

    init {
        fetchChildrenDashboard()
    }

    private fun fetchChildrenDashboard() {
        viewModelScope.launch(ioContext) {
            _state.value = ChildrenListState.Loading

            val family = getFamilyFromLocalUseCase()

            val childrenList = getChildrenDashboardUseCase(family.familyId)

            _state.value = ChildrenListState.Success(
                children = childrenList
            )
        }
    }

    fun onChildClick(childId: String) {
        viewModelScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToDestination(
                    Destination.PARENT_CHILD_DETAILS
                )
            )
        }
    }

    fun onBackPressed() {

    }
}