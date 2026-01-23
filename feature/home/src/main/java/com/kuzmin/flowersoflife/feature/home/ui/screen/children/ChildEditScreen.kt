package com.kuzmin.flowersoflife.feature.home.ui.screen.children

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.core.ui.components.button.BaseApproveBtnGroup
import com.kuzmin.flowersoflife.core.ui.components.button.SecondaryButton
import com.kuzmin.flowersoflife.core.ui.components.text.BaseTextInputField
import com.kuzmin.flowersoflife.feature.home.exception.error.ChildEditErrorType
import com.kuzmin.flowersoflife.feature.home.ui.model.ChildUi
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.state.BaseChildState
import com.kuzmin.flowersoflife.feature.home.ui.screen.children.viewmodel.ChildEditViewModel
import org.koin.androidx.compose.koinViewModel
import com.kuzmin.flowersoflife.core.ui.R as CoreUiRes

@Composable
fun ChildEditScreen(
    modifier: Modifier = Modifier,
    viewModel: ChildEditViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsState()
    val errors by viewModel.errors.collectAsState()

    when (val uiState = state) {
        is BaseChildState.Success -> {
            ChildEditContent(
                modifier = modifier,
                state = uiState,
                errors = errors,
                onChildChange = viewModel::onChildChange,
                onAddPhotoClick = viewModel::onAddPhotoClick,
                onCancelClick = viewModel::onCancelClick,
                onSaveClick = viewModel::onSaveClick,
            )
        }
        is BaseChildState.Error -> {

        }
        is BaseChildState.Loading -> {

        }
    }
}

@Composable
fun ChildEditContent(
    modifier: Modifier = Modifier,
    state: BaseChildState.Success<ChildUi>,
    errors: Set<ChildEditErrorType> = emptySet(),
    onChildChange: (ChildUi.() -> ChildUi) -> Unit,
    onAddPhotoClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        BaseTextInputField(
            value = state.data.childName,
            label = stringResource(id = R.string.username),
            onValueChange = {
                onChildChange {
                    copy(childName = it.trim())
                }
            },
            isError = errors.contains(ChildEditErrorType.CHILD_NAME_EMPTY),
            supportingText = if (errors.contains(ChildEditErrorType.CHILD_NAME_EMPTY)) {
                stringResource(id = R.string.error_group_name_empty)
            } else null
        )

        AsyncImage(
            model = state.data.photoUrl,
            contentDescription = "Avatar ${state.data.childName}",
            modifier = Modifier
                .size(width = 226.dp, height = 305.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = CoreUiRes.drawable.avatar_placeholder),
            error = painterResource(id = CoreUiRes.drawable.avatar_placeholder)
        )

        SecondaryButton(
            text = stringResource(id = R.string.add_photo_btn_txt),
            onClick = onAddPhotoClick,
            modifier = Modifier
                .width(226.dp)
        )

        BaseApproveBtnGroup(
            positiveText = stringResource(id = R.string.save_btn_txt),
            negativeText = stringResource(id = R.string.cancel_btn_txt),
            onPositiveClick = {

            },
            onNegativeClick = {

            }
        )
    }
}

