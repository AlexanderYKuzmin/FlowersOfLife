package com.kuzmin.flowersoflife.core.ui.components.checkbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.core.ui.theme.KabTheme
import com.kuzmin.flowersoflife.core.ui.theme.SemiBold18

@Composable
fun BaseCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = KabTheme.colors.primary,
        uncheckedColor = KabTheme.colors.frameInactive,
        checkmarkColor = KabTheme.colors.primaryText
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
                text = label,
                style = SemiBold18,
                color = KabTheme.colors.primaryOnCardText
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
    KabTheme {
        BaseCheckbox(
            checked = true,
            onCheckedChange = {},
            label = "Remember me"
        )
    }
}