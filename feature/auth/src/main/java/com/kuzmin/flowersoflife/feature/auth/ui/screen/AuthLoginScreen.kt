package com.kuzmin.flowersoflife.feature.auth.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.core.ui.theme.FlowersOfLifeTheme
import com.kuzmin.flowersoflife.core.ui.theme.Link
import com.kuzmin.flowersoflife.core.ui.components.button.BaseApproveBtnGroup
import com.kuzmin.flowersoflife.core.ui.components.checkbox.BaseCheckbox
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.components.text.BasePasswordInputField
import com.kuzmin.flowersoflife.core.ui.components.text.BaseTextInputField
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalLoginException
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalRouteException
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType
import com.kuzmin.flowersoflife.feature.auth.ui.viewmodels.AuthLoginViewModel

@Composable
fun AuthLoginScreen(
    viewModel: AuthLoginViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsState()

    val errors by viewModel.fieldErrors.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.castle_pic),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        when (authState) {
            is AuthState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is AuthState.Success -> {
                AuthLoginScreenCard(
                    email = (authState as AuthState.Success).user.email,
                    password = (authState as AuthState.Success).user.password,
                    onNegativeClick = viewModel::cancelAuth,
                    onPositiveClick = {  credentials, rememberMe ->
                        viewModel.signInUser(credentials, rememberMe)
                    },
                    navigateToRegisterUser = viewModel::navigateToRegisterUser,
                    errors = errors
                )
            }

            is AuthState.Error -> {
                Log.d("CAB-8", "AuthLoginScreen: $authState")
                val state = authState as AuthState.Error
                val message = when (state.throwable) {
                    is IllegalRouteException -> stringResource(id = R.string.illegal_route_error)
                    is IllegalLoginException -> stringResource(id = R.string.illegal_login_error)
                    else -> state.throwable.message ?: stringResource(id = R.string.unknown_error)
                }

                viewModel.showSnackMessage(
                    message = message,
                    type = SnackbarMessageType.ERROR
                )
                viewModel.refresh()
            }

            else -> Unit
        }
    }
}

@Composable
fun AuthLoginScreenCard(
    email: String = "",
    password: String = "",
    navigateToRegisterUser: () -> Unit = {},
    onPositiveClick: (AuthCredentials, Boolean) -> Unit = { _, _ -> },
    onNegativeClick: () -> Unit = {},
    errors: Set<RegisterErrorType> = emptySet()
) {
    var credentials by remember { mutableStateOf(AuthCredentials(email, password)) }
    var rememberMe by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val imeVisible = WindowInsets.ime.getBottom(density) > 0

    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                BaseTextInputField(
                    value = credentials.email,
                    onValueChange = {
                        credentials = credentials.copy(email = it)
                    },
                    label = stringResource(id = R.string.email),
                    isError = errors.contains(RegisterErrorType.EMAIL_EMPTY) || errors.contains(RegisterErrorType.EMAIL_INVALID),
                    supportingText = when {
                        errors.contains(RegisterErrorType.EMAIL_EMPTY) -> stringResource(id = R.string.error_email_empty)
                        errors.contains(RegisterErrorType.EMAIL_INVALID) -> stringResource(id = R.string.error_email_invalid)
                        else -> null
                    }
                )

                BasePasswordInputField(
                    value = credentials.password,
                    onValueChange = { credentials = credentials.copy(password = it) },
                    label = stringResource(id = R.string.password),
                    isError = errors.contains(RegisterErrorType.PASSWORD_EMPTY) || errors.contains(RegisterErrorType.PASSWORD_WEAK),
                    supportingText = when {
                        errors.contains(RegisterErrorType.PASSWORD_EMPTY) -> stringResource(id = R.string.error_password_empty)
                        errors.contains(RegisterErrorType.PASSWORD_WEAK) -> stringResource(id = R.string.error_password_weak)
                        else -> null
                    }
                )


                if (!imeVisible) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        BaseCheckbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            label = stringResource(id = R.string.remember_me),
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                uncheckedColor = MaterialTheme.colorScheme.outline,
                                checkmarkColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = stringResource(id = R.string.register),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Link,
                                textDecoration = TextDecoration.Underline
                            ),
                            modifier = Modifier.clickable(
                                onClick = navigateToRegisterUser
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    BaseApproveBtnGroup(
                        positiveText = stringResource(id = R.string.ok_btn_txt),
                        negativeText = stringResource(id = R.string.cancel_btn_txt),
                        onPositiveClick = { onPositiveClick(credentials, rememberMe) },
                        onNegativeClick = { onNegativeClick() }
                    )
                } else {
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    apiLevel = 33
)
@Composable
fun AuthLoginScreenPreview() {
    FlowersOfLifeTheme {
        AuthLoginScreenCard(
            email = "gpBzK@example.com",
            password = "123456"
        )
    }
}
