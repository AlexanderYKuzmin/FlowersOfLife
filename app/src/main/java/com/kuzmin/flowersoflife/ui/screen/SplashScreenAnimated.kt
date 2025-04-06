package com.kuzmin.flowersoflife.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.R
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SplashScreenAnimated(
    modifier: Modifier = Modifier
) {
    val leafletCount = 8
    val leafletStates = remember {
        List(leafletCount) {
            mutableStateOf(false)
        }
    }

    listOf(500L, 600L, 700L).forEach { duration ->
        LaunchedEffect(duration) {
            while (true) {
                val index = Random.nextInt(leafletCount)
                leafletStates[index].value = true
                delay(duration)
                leafletStates[index].value = false
                delay(200L)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.splash_screen),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )

        leafletStates.forEachIndexed { index, isVisible ->
            val offset = randomOffsetForIndex(index)
            val targetAlpha by animateFloatAsState(
                targetValue = if (isVisible.value) 0.8f else 0f,
                animationSpec = tween(durationMillis = 500), label = ""
            )

            AnimatedVisibility(
                visible = isVisible.value,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Box(
                    modifier = Modifier
                        .offset(offset.x, offset.y)
                        .size(40.dp, 50.dp)
                        .alpha(targetAlpha)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(6.dp)
                        )
                )
            }
        }
    }
}

fun randomOffsetForIndex(index: Int): DpOffset {
    val positions = listOf(
        DpOffset(80.dp, 80.dp), DpOffset(140.dp, 100.dp), DpOffset(200.dp, 150.dp),
        DpOffset(100.dp, 250.dp), DpOffset(60.dp, 300.dp), DpOffset(180.dp, 320.dp),
        DpOffset(240.dp, 210.dp), DpOffset(120.dp, 180.dp)
    )
    return positions.getOrElse(index) { DpOffset(0.dp, 0.dp) }
}
