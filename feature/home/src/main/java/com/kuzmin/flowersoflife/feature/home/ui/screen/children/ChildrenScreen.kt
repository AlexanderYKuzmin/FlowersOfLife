package com.kuzmin.flowersoflife.feature.home.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.ChildrenListState

@Composable
fun ChildrenScreen(
    modifier: Modifier = Modifier,
    state: ChildrenListState,
    onBackPressed: () -> Unit = {},
    onChildClick: (childId: String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {

        when(state) {
            is ChildrenListState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

            is ChildrenListState.Success -> {
                ChildrenScreenContent(state.children)
            }

            is ChildrenListState.Error -> {
                //TODO Error Alert
            }
        }
    }
}

@Composable
fun ChildrenScreenContent(
    children: List<Child>
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        repeat(children.size) { index ->
            //TODO ChildCard
        }
    }
}