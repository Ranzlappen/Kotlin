package io.github.ranzlappen.template.core.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Spacing scale for the design system. Use these instead of raw dp values so
 * layout rhythm stays consistent and can be tuned in one place.
 */
data class Spacing(
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }
