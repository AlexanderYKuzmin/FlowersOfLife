package com.kuzmin.flowersoflife.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.kuzmin.flowersoflife.R

@Composable
fun DrawerContent() {
    Column {
        Text(text = stringResource(id = R.string.drawer_option1))
        Text(text = stringResource(id = R.string.drawer_option2))
        // Add additional drawer items here
    }
}