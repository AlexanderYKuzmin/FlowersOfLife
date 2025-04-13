package com.kuzmin.flowersoflife.feature.auth.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.common.ui.theme.FlowersOfLifeTheme
import com.kuzmin.flowersoflife.common.ui.theme.Link
import com.kuzmin.flowersoflife.core.domain.extensions.isEmailConsistent
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.ui.components.components.button.BaseApproveBtnGroup
import com.kuzmin.flowersoflife.core.ui.components.components.checkbox.BaseCheckbox
import com.kuzmin.flowersoflife.core.ui.components.components.text.BasePasswordInputField
import com.kuzmin.flowersoflife.core.ui.components.components.text.BaseTextInputField
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthState
import com.kuzmin.flowersoflife.feature.auth.ui.viewmodels.AuthLoginViewModel

@Composable
fun AuthLoginScreen(
    viewModel: AuthLoginViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsState()

    var credentials by remember { mutableStateOf(AuthCredentials()) }
    var rememberMe by remember { mutableStateOf(false) }

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
                credentials = AuthCredentials(
                    email = (authState as AuthState.Success).user.email,
                    password = credentials.password
                )
            }

            is AuthState.Error -> {
                // TODO
            }

            else -> {}
        }

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

                var isEmailValid by remember { mutableStateOf(false) }
                BaseTextInputField(
                    value = credentials.email,
                    onValueChange = {
                        credentials = credentials.copy(email = it)
                        isEmailValid = it.isEmailConsistent()
                    },
                    label = stringResource(id = R.string.email),
                    isError = !isEmailValid,
                    supportingText = if (!isEmailValid) "Некорректный email" else null
                )

                BasePasswordInputField(
                    value = credentials.password,
                    onValueChange = { credentials = credentials.copy(password = it) },
                    label = stringResource(id = R.string.password)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
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
                    Text(
                        text = stringResource(id = R.string.register),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Link,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier.clickable(
                            onClick = { viewModel.navigateToRegisterUser() }
                        )
                    )
                }

                BaseApproveBtnGroup(
                    positiveText = stringResource(id = R.string.ok_btn_txt),
                    negativeText = stringResource(id = R.string.cancel_btn_txt),
                    onPositiveClick = { viewModel.signInUser(credentials, rememberMe) },
                    onNegativeClick = { viewModel.cancelAuth() }
                )
            }
        }
    }
}
