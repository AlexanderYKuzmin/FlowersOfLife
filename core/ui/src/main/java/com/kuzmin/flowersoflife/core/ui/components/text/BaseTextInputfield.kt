package com.kuzmin.flowersoflife.core.ui.components.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.Regular16

@Composable
fun BaseTextInputField(
    value: String,
    onValueChange: (String) -> Unit = {},
    label: String,
    isError: Boolean = false,
    supportingText: String? = null,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        textStyle = Regular16,
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = KabTheme.colors.frameActive,
            unfocusedBorderColor = KabTheme.colors.frameInactive,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedLabelColor = KabTheme.colors.frameInactive,
            focusedLabelColor = KabTheme.colors.frameActive,
            focusedTextColor = KabTheme.colors.primaryOnCardText,
            unfocusedTextColor = KabTheme.colors.primaryDimmedText
        ),
        isError = isError,
        supportingText = {
            supportingText?.let { Text(it) }
        },
        readOnly = readOnly
    )
}

@Preview(
    showBackground = true
)
@Composable
fun BaseTextInputFieldPreview() {
    KabTheme {
        BaseTextInputField(
            value = "Test",
            label = "Test"
        )
    }
}