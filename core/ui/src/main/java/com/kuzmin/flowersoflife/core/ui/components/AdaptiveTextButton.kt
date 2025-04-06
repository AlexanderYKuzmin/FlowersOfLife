package com.kuzmin.flowersoflife.core.ui.components

import android.util.Log
import androidx.annotation.Px
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuzmin.flowersoflife.common.ext.toPx

@Composable
fun AdaptiveTextButton(
    buttonText: String,
    buttonWidth: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    if (buttonWidth == 0) return

    val textOffset = 56.dp.toPx()
    val textMeasurer = rememberTextMeasurer()
    var fontSize by remember(buttonWidth) { mutableStateOf(22.sp) }

    LaunchedEffect(buttonText, buttonWidth) {
        var currentFontSize = fontSize
        var textWidthPx: Float
        while (true) {
            val textLayoutResult = textMeasurer.measure(
                text = AnnotatedString(buttonText),
                style = TextStyle(fontSize = currentFontSize)
            )
            textWidthPx = textLayoutResult.size.width + textOffset

            if (textWidthPx <= buttonWidth) { break }
            currentFontSize = (currentFontSize.value - 1).sp
        }
        fontSize = currentFontSize
    }

    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = buttonText,
            fontSize = fontSize,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}