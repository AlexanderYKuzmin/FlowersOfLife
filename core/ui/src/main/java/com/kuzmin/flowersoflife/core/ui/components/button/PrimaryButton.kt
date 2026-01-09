package com.kuzmin.flowersoflife.core.ui.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.core.ui.theme.Bold18
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = KabTheme.colors.surface,
            contentColor = KabTheme.colors.primaryText
        )
    ) {
        Text(
            text,
            style = Bold18
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    KabTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            PrimaryButton(text = "OK", onClick = {})
        }
    }
}

