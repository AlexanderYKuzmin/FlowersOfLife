package com.kuzmin.flowersoflife.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class KabColorScheme(
    // Main colors
    val primaryDark: Color,
    val primary: Color,
    val surface: Color,

    // Card
    val simpleCardBgd: Color,

    // Text colors
    val primaryText: Color,
    val infoText: Color,
    val warningText: Color,
    val errorText: Color,
    val successText: Color,
    val primaryOnCardText: Color,
    val primaryDimmedText: Color,

    // Progress
    val successProgress: Color,
    val progressBgd: Color,
    val progressBgdDark: Color,

    // Tag
    val yellowPaleTag: Color,
    val redPaleTag: Color,

    // Frame
    val frameActive: Color,
    val frameInactive: Color,

    val grayLight: Color,

    val cardDetailsGradStart: Color,
    val cardDetailsGradEnd: Color,
) {
    companion object {
        @Composable
        fun defaultLightColors() = KabColorScheme(
            primaryDark = PrimaryDark,
            primary = Primary,
            surface = Surface,
            simpleCardBgd = SimpleCardBgd,
            primaryText = PrimaryText,
            infoText = InfoText,
            warningText = WarningText,
            errorText = ErrorText,
            successText = SuccessText,
            primaryOnCardText = PrimaryOnCardText,
            primaryDimmedText = PrimaryDimmedText,
            successProgress = SuccessProgress,
            progressBgd = ProgressBgd,
            progressBgdDark = ProgressBgdDark,
            yellowPaleTag = YellowPaleTag,
            redPaleTag = RedPaleTag,
            frameActive = FrameActive,
            frameInactive = FrameInactive,
            grayLight = GrayLight,
            cardDetailsGradStart = CardDetailsGradStart,
            cardDetailsGradEnd = CardDetailsGradEnd,
        )

        @Composable
        fun defaultDarkColors() = KabColorScheme(
            primaryDark = PrimaryDark,
            primary = Primary,
            surface = Surface,
            simpleCardBgd = SimpleCardBgd,
            primaryText = PrimaryText,
            infoText = InfoText,
            warningText = WarningText,
            errorText = ErrorText,
            successText = SuccessText,
            primaryOnCardText = PrimaryOnCardText,
            primaryDimmedText = PrimaryDimmedText,
            successProgress = SuccessProgress,
            progressBgd = ProgressBgd,
            progressBgdDark = ProgressBgdDark,
            yellowPaleTag = YellowPaleTag,
            redPaleTag = RedPaleTag,
            frameActive = FrameActive,
            frameInactive = FrameInactive,
            grayLight = GrayLight,
            cardDetailsGradStart = CardDetailsGradStart,
            cardDetailsGradEnd = CardDetailsGradEnd,
        )
    }
}