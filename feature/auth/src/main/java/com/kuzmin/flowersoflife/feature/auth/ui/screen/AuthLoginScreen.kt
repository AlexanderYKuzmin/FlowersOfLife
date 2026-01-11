package com.kuzmin.flowersoflife.feature.auth.ui.screen

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.core.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.core.ui.components.button.BaseApproveBtnGroup
import com.kuzmin.flowersoflife.core.ui.components.snackbar.SnackbarMessageType
import com.kuzmin.flowersoflife.core.ui.components.text.BasePasswordInputField
import com.kuzmin.flowersoflife.core.ui.components.text.BaseTextInputField
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold16
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalLoginException
import com.kuzmin.flowersoflife.feature.auth.exception.IllegalRouteException
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType.EMAIL_EMPTY
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType.EMAIL_INVALID
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType.PASSWORD_EMPTY
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType.PASSWORD_WEAK
import com.kuzmin.flowersoflife.feature.auth.ui.viewmodels.AuthLoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthLoginScreen(
    viewModel: AuthLoginViewModel = koinViewModel(),
) {
    val authState by viewModel.authState.collectAsState()

    val errors by viewModel.fieldErrors.collectAsState()

    LaunchedEffect(key1 = Unit) { viewModel.notifyTopBarDataChanged() }

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
                    email = (authState as AuthState.Success).userFamily.user.email,
                    password = "",
                    onNegativeClick = viewModel::cancelAuth,
                    onPositiveClick = { credentials ->
                        viewModel.signInUser(credentials)
                    },
                    navigateToRegisterUser = viewModel::navigateToRegisterUser,
                    errors = errors
                )
            }

            is AuthState.Error -> {
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
    onPositiveClick: (AuthCredentials) -> Unit = { _ -> },
    onNegativeClick: () -> Unit = {},
    errors: Set<RegisterErrorType> = emptySet()
) {
    var credentials by remember { mutableStateOf(AuthCredentials(email, password)) }

    val density = LocalDensity.current
    val imeVisible = WindowInsets.ime.getBottom(density) > 0

    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = KabTheme.colors.simpleCardBgd),
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
                    isError = errors.contains(EMAIL_EMPTY) || errors.contains(EMAIL_INVALID),
                    supportingText = when {
                        errors.contains(EMAIL_EMPTY) -> stringResource(id = R.string.error_email_empty)
                        errors.contains(EMAIL_INVALID) -> stringResource(id = R.string.error_email_invalid)
                        else -> null
                    }
                )

                BasePasswordInputField(
                    value = credentials.password,
                    onValueChange = { credentials = credentials.copy(password = it) },
                    label = stringResource(id = R.string.password),
                    isError = errors.contains(PASSWORD_EMPTY) || errors.contains(PASSWORD_WEAK),
                    supportingText = when {
                        errors.contains(PASSWORD_EMPTY) -> stringResource(id = R.string.error_password_empty)
                        errors.contains(PASSWORD_WEAK) -> stringResource(id = R.string.error_password_weak)
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
                        Text(
                            text = stringResource(id = R.string.register),
                            style = SemiBold16.copy(
                                color = KabTheme.colors.infoText,
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
                        onPositiveClick = { onPositiveClick(credentials) },
                        onNegativeClick = { onNegativeClick() }
                    )
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
    KabTheme {
        AuthLoginScreenCard(
            email = "gpBzK@example.com",
            password = "123456"
        )
    }
}
