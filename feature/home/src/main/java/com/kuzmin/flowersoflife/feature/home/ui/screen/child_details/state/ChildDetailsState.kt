package com.kuzmin.flowersoflife.feature.home.ui.screen.child_details.state

import com.kuzmin.flowersoflife.core.domain.model.family_members.ChildDetails

sealed interface ChildDetailsState {

    data object Loading : ChildDetailsState

    data class Success(
        val childDetails: ChildDetails
    ) : ChildDetailsState

    data class Error(val message: String) : ChildDetailsState
}