package com.kuzmin.flowersoflife.feature.auth.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.core.domain.extensions.orFalse
import com.kuzmin.flowersoflife.core.domain.model.Family
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.core.ui.components.button.BaseApproveBtnGroup
import com.kuzmin.flowersoflife.core.ui.components.checkbox.BaseCheckbox
import com.kuzmin.flowersoflife.core.ui.components.text.BasePasswordInputField
import com.kuzmin.flowersoflife.core.ui.components.text.BaseTextInputField
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType
import com.kuzmin.flowersoflife.feature.auth.ui.viewmodels.AuthRegisterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthRegisterScreen(
    viewModel: AuthRegisterViewModel = koinViewModel()
) {
    val userFamily by viewModel.userState.collectAsState()

    val errors by viewModel.fieldErrors.collectAsState()

    val repeatPassword by viewModel.repeatPassword.collectAsState()
    val passwordMismatch = viewModel.isPasswordMismatch()

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(Unit) { viewModel.notifyTopBarDataChanged() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.tech_bgd_narrow),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        when (authState) {
            is AuthState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is AuthState.Error -> {
                // TODO: handle error
            }

            is AuthState.Success -> AuthRegisterScreen(
                userFamily = userFamily,
                errors = errors,
                repeatPassword = repeatPassword,
                passwordMismatch = passwordMismatch,
                onRoleChange = viewModel::updateRole,
                onUsersFieldChange = viewModel::updateUserField,
                onFamilyFieldChange = viewModel::updateFamilyField,
                onRepeatPasswordChange = viewModel::onRepeatPasswordChanged,
                registerUser = viewModel::registerUser,
                cancelRegistration = viewModel::cancelRegistration
            )

            else -> Unit
        }
    }

}

@Composable
fun AuthRegisterScreen(
    userFamily: UserFamily?,
    errors: Set<RegisterErrorType> = emptySet(),
    repeatPassword: String = "",
    passwordMismatch: Boolean = false,
    onRoleChange: (UserRole?) -> Unit = {},
    onUsersFieldChange: (User.() -> User) -> Unit = {},
    onFamilyFieldChange: (Family.() -> Family) -> Unit = {},
    onRepeatPasswordChange: (String) -> Unit = {},
    registerUser: () -> Unit = {},
    cancelRegistration: () -> Unit = {}
) {
    val isParent = when (userFamily?.user?.role) {
        UserRole.PARENT -> true
        else -> false
    }

    val isChild = !isParent

    val density = LocalDensity.current
    val imeVisible = WindowInsets.ime.getBottom(density) > 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            val rowModifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)

            if (!imeVisible) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = rowModifier,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StyledTextOnSurface(text = stringResource(id = R.string.i_am_parent))
                    BaseCheckbox(
                        checked = isParent,
                        onCheckedChange = {
                            onRoleChange(if (it) UserRole.PARENT else null)
                        }
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
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            BaseTextInputField(
                modifier = rowModifier.padding(bottom = 4.dp),
                value = userFamily?.family?.familyName ?: "",
                label = stringResource(id = R.string.family_group),
                onValueChange = {
                    onFamilyFieldChange { copy(familyName = it.trim()) }
                },
                isError = errors.contains(RegisterErrorType.FAMILY_NAME_EMPTY),
                supportingText = if (errors.contains(RegisterErrorType.FAMILY_NAME_EMPTY)) {
                    stringResource(id = R.string.error_group_name_empty)
                } else null
            )
            BaseTextInputField(
                modifier = rowModifier.padding(bottom = 4.dp),
                value = userFamily?.user?.name ?: "",
                label = stringResource(id = R.string.username),
                onValueChange = {
                    onUsersFieldChange { copy(name = it.trim()) }
                },
                isError = errors.contains(RegisterErrorType.USERNAME_IS_EMPTY),
                supportingText = if (errors.contains(RegisterErrorType.USERNAME_IS_EMPTY)) {
                    stringResource(id = R.string.error_firstname_is_empty)
                } else null
            )
            BaseTextInputField(
                modifier = rowModifier.padding(bottom = 4.dp),
                value = userFamily?.user?.email ?: "",
                label = stringResource(id = R.string.email),
                onValueChange = {
                    onUsersFieldChange { copy(email = it) }
                },
                isError = errors.contains(RegisterErrorType.EMAIL_EMPTY) || errors.contains(
                    RegisterErrorType.EMAIL_INVALID
                ),
                supportingText = when {
                    errors.contains(RegisterErrorType.EMAIL_EMPTY) -> stringResource(id = R.string.error_email_empty)
                    errors.contains(RegisterErrorType.EMAIL_INVALID) -> stringResource(id = R.string.error_email_invalid)
                    else -> null
                }
            )
           /* BaseTextInputField(
                modifier = rowModifier.padding(bottom = 4.dp),
                value = userFamily?.family?.familyCode ?: "",
                label = stringResource(id = R.string.family_code),
                onValueChange = {
                    onFamilyFieldChange { copy(familyCode = it) }
                },
                isError = false,
                readOnly = isAdmin
            )*/
            BasePasswordInputField(
                modifier = rowModifier.padding(bottom = 8.dp),
                value = userFamily?.user?.password ?: "",
                label = stringResource(id = R.string.password),
                onValueChange = {
                    onUsersFieldChange { copy(password = it) }
                },
                isError = errors.contains(RegisterErrorType.PASSWORD_EMPTY) || errors.contains(
                    RegisterErrorType.PASSWORD_WEAK
                ),
                supportingText = when {
                    errors.contains(RegisterErrorType.PASSWORD_EMPTY) -> stringResource(id = R.string.error_password_empty)
                    errors.contains(RegisterErrorType.PASSWORD_WEAK) -> stringResource(id = R.string.error_password_weak)
                    else -> null
                }
            )
            BasePasswordInputField(
                value = repeatPassword,
                label = stringResource(id = R.string.password_again),
                onValueChange = {
                    onRepeatPasswordChange(it)
                },
                isError = passwordMismatch,
                supportingText = if (passwordMismatch) stringResource(id = R.string.error_password_mismatch) else null
            )
        }

        if (!imeVisible) Spacer(modifier = Modifier.weight(0.02f))

        BaseApproveBtnGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            positiveText = stringResource(id = R.string.ok_btn_txt),
            negativeText = stringResource(id = R.string.cancel_btn_txt),
            onPositiveClick = registerUser,
            onNegativeClick = cancelRegistration
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
    KabTheme {
        AuthRegisterScreen(
            userFamily = UserFamily(
                User(
                    userId = "",
                    role = UserRole.PARENT,
                    isAdmin = true,
                    familyId = ""
                ),
                Family(
                    familyId = "",
                    familyName = "",
                    familyCode = ""
                )
            )
        )
    }
}