package com.kuzmin.flowersoflife.feature.home.ui.screen.child_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kuzmin.flowersoflife.core.domain.repository.FamilyRepository
import com.kuzmin.flowersoflife.core.navigation.routing.DestinationArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChildViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: FamilyRepository
) : ViewModel() {

    init {
        val childId: String = checkNotNull(savedStateHandle[DestinationArgs.CHILD_ID])
    }

    private fun fetchChild(childId: String) {

    }
}