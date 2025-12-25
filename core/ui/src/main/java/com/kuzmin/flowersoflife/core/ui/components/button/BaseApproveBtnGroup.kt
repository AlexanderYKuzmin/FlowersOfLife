package com.kuzmin.flowersoflife.core.ui.components.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.common.R
import com.kuzmin.flowersoflife.core.ui.theme.FlowersOfLifeTheme

@Composable
fun BaseApproveBtnGroup(
    positiveText: String,
    negativeText: String,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            //.padding(16.dp)
    ) {
        SecondaryButton(
            modifier = Modifier.weight(1f),
            text = negativeText,
            onClick = { onNegativeClick() }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        PrimaryButton(
            modifier = Modifier.weight(1f),
            text = positiveText,
            onClick = { onPositiveClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BaseApproveBtnGroupPreview() {
    FlowersOfLifeTheme(dynamicColor = false) {
        BaseApproveBtnGroup(
            positiveText = stringResource(id = R.string.ok_btn_txt),
            negativeText = stringResource(id = R.string.cancel_btn_txt),
            onPositiveClick = {},
            onNegativeClick = {}
        )
    }
}
