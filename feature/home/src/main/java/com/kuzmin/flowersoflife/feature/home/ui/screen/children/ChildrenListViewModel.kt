package com.kuzmin.flowersoflife.feature.home.ui.screen.children

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChildrenListViewModel @Inject constructor(

) : ViewModel() {

    val _state = MutableStateFlow()
}