package com.kuzmin.flowersoflife.feature.auth.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.common.ui.theme.FlowersOfLifeTheme
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.ui.components.components.button.BaseApproveBtnGroup
import com.kuzmin.flowersoflife.core.ui.components.components.checkbox.BaseCheckbox
import com.kuzmin.flowersoflife.core.ui.components.components.text.BasePasswordInputField
import com.kuzmin.flowersoflife.core.ui.components.components.text.BaseTextInputField
import com.kuzmin.flowersoflife.feature.auth.ui.viewmodels.AuthRegisterViewModel

@Composable
fun AuthRegisterScreen(
    authRegisterViewModel: AuthRegisterViewModel = hiltViewModel()
) {
    val user by authRegisterViewModel.userState.collectAsState()

    AuthRegisterScreen(
        user = user,
        onRoleChange = authRegisterViewModel::updateRole,
        onAdminChange = authRegisterViewModel::updateIsAdmin,
        onFieldChange = authRegisterViewModel::updateUserField
    )
}

@Composable
fun AuthRegisterScreen(
    user: User?,
    onRoleChange: (UserRole?) -> Unit = {},
    onAdminChange: (Boolean) -> Unit = {},
    onFieldChange: (User.() -> User) -> Unit = {}
) {
    val isParent = user?.role == UserRole.PARENT
    val isChild = user?.role == UserRole.CHILD
    val isAdmin = user?.isAdmin ?: false

    Column {
        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)

        Row(modifier = rowModifier, horizontalArrangement = Arrangement.SpaceBetween) {
            StyledTextOnSurface(text = stringResource(id = R.string.i_am_parent))
            BaseCheckbox(
                checked = isParent,
                onCheckedChange = {
                    onRoleChange(if (it) UserRole.PARENT else null)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Row(modifier = rowModifier, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(id = R.string.i_am_child),
                fontWeight = FontWeight.Bold
            )
            BaseCheckbox(
                checked = isChild,
                onCheckedChange = {
                    onRoleChange(if (it) UserRole.CHILD else null)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Row(modifier = rowModifier, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = stringResource(id = R.string.i_am_admin),
                fontWeight = FontWeight.Bold
            )
            BaseCheckbox(
                checked = isAdmin,
                onCheckedChange = { onAdminChange(it) },
                enabled = true,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.outline,
                    checkmarkColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                BaseTextInputField(
                    value = user?.groupName ?: "",
                    label = stringResource(id = R.string.family_group),
                    onValueChange = {
                        onFieldChange { copy(groupName = it) }
                    }
                )
                BaseTextInputField(
                    value = user?.firstName ?: "",
                    label = stringResource(id = R.string.firstname),
                    onValueChange = {
                        onFieldChange { copy(firstName = it) }
                    }
                )
                BaseTextInputField(
                    value = user?.email ?: "",
                    label = stringResource(id = R.string.email),
                    onValueChange = {
                        onFieldChange { copy(email = it) }
                    }
                )
                BasePasswordInputField(
                    value = user?.password ?: "",
                    label = stringResource(id = R.string.password),
                    onValueChange = {
                        TODO()
                    }
                )
                BasePasswordInputField(
                    value = "",
                    label = stringResource(id = R.string.password_again),
                    onValueChange = {
                        TODO()
                    }
                )
            }
        }

        BaseApproveBtnGroup(
            positiveText = stringResource(id = R.string.ok_btn_txt),
            negativeText = stringResource(id = R.string.cancel_btn_txt),
            onPositiveClick = { TODO() },
            onNegativeClick = { TODO() }
        )
    }
}

@Composable
fun StyledTextOnSurface(
    text: String
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
    )
}

@Preview(showBackground = true)
@Composable
fun AuthRegisterScreenPreview() {
    FlowersOfLifeTheme {
        AuthRegisterScreen(
            user = User(role = UserRole.PARENT, isAdmin = true)
        )
    }
}