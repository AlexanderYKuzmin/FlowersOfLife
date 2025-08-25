package com.kuzmin.flowersoflife.core.ui.components.checkbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.core.ui.theme.FlowersOfLifeTheme

@Composable
fun BaseCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colorScheme.primary,
        uncheckedColor = MaterialTheme.colorScheme.outline,
        checkmarkColor = MaterialTheme.colorScheme.onPrimary
    )
) {
    Row(
        modifier = modifier
            .wrapContentHeight()
            .clickable(enabled = enabled) { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (label != null) {
            Text(
                text = label ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            colors = colors
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun BaseCheckboxPreview() {
    FlowersOfLifeTheme {
        BaseCheckbox(
            checked = true,
            onCheckedChange = {},
            label = "Remember me"
        )
    }
}