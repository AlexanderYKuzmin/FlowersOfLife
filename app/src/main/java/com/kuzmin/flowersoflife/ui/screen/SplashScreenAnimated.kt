package com.kuzmin.flowersoflife.ui.screen

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.kuzmin.flowersoflife.R
import com.kuzmin.flowersoflife.common.ext.setSystemBarsAppearance
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SplashScreenAnimated(
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as Activity

    SideEffect {
        activity.setSystemBarsAppearance()
    }

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
