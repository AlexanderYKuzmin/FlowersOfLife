package com.kuzmin.flowersoflife.feature.home.ui.screen.children

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuzmin.flowersoflife.core.domain.repository.FamilyRepository
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import com.kuzmin.flowersoflife.core.navigation.routing.Destination
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.ChildrenListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChildrenListViewModel @Inject constructor(
    private val repository: FamilyRepository,
    private val prefManager: PrefManager,
    private val navigationManager: NavigationManager
) : ViewModel() {
    private val _state = MutableStateFlow<ChildrenListState>(ChildrenListState.Loading)
    val state = _state.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }

    private val ioContext = Dispatchers.IO + coroutineExceptionHandler

    init {
        fetchChildrenList()
    }

    private fun fetchChildrenList() {
        viewModelScope.launch(ioContext) {
            val uid = prefManager.getUser().uid

            uid?.let {
                val result = repository.getChildrenDetailsList(it)

                _state.update {
                    ChildrenListState.Success(
                        children = result
                    )
                }
            } ?: throw Exception("uid is null")
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