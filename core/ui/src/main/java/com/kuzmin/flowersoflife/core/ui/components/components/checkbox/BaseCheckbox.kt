package com.kuzmin.flowersoflife.core.ui.components.components.checkbox

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
import com.kuzmin.flowersoflife.common.ui.theme.FlowersOfLifeTheme

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
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            colors = colors
        )
        if (label != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = label ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
        }
    }
}

@Preview
@Composable
fun BaseCheckboxPreview() {
    FlowersOfLifeTheme(dynamicColor = false) {
        BaseCheckbox(
            checked = true,
            onCheckedChange = {},
            label = "test"
        )
    }
}