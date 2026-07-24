package io.github.ranzlappen.template.core.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Baseline brand palette. Replace these seeds when branding a real app;
// dynamic color (Android 12+) overrides them when the user enables it.
internal val Primary = Color(0xFF4A5C92)
internal val OnPrimary = Color(0xFFFFFFFF)
internal val PrimaryContainer = Color(0xFFDCE1FF)
internal val OnPrimaryContainer = Color(0xFF001550)
internal val Secondary = Color(0xFF595D72)
internal val OnSecondary = Color(0xFFFFFFFF)
internal val SecondaryContainer = Color(0xFFDEE1F9)
internal val OnSecondaryContainer = Color(0xFF161B2C)
internal val Tertiary = Color(0xFF75546F)
internal val OnTertiary = Color(0xFFFFFFFF)
internal val TertiaryContainer = Color(0xFFFFD7F5)
internal val OnTertiaryContainer = Color(0xFF2C122A)
internal val Error = Color(0xFFBA1A1A)
internal val OnError = Color(0xFFFFFFFF)
internal val ErrorContainer = Color(0xFFFFDAD6)
internal val OnErrorContainer = Color(0xFF410002)
internal val Background = Color(0xFFFAF8FF)
internal val OnBackground = Color(0xFF1A1B21)
internal val Surface = Color(0xFFFAF8FF)
internal val OnSurface = Color(0xFF1A1B21)
internal val SurfaceVariant = Color(0xFFE2E1EC)
internal val OnSurfaceVariant = Color(0xFF45464F)
internal val Outline = Color(0xFF767680)

internal val DarkPrimary = Color(0xFFB4C4FF)
internal val DarkOnPrimary = Color(0xFF1A2D60)
internal val DarkPrimaryContainer = Color(0xFF324478)
internal val DarkOnPrimaryContainer = Color(0xFFDCE1FF)
internal val DarkSecondary = Color(0xFFC2C5DD)
internal val DarkOnSecondary = Color(0xFF2B3042)
internal val DarkSecondaryContainer = Color(0xFF414659)
internal val DarkOnSecondaryContainer = Color(0xFFDEE1F9)
internal val DarkTertiary = Color(0xFFE3BADA)
internal val DarkOnTertiary = Color(0xFF432740)
internal val DarkTertiaryContainer = Color(0xFF5B3D57)
internal val DarkOnTertiaryContainer = Color(0xFFFFD7F5)
internal val DarkError = Color(0xFFFFB4AB)
internal val DarkOnError = Color(0xFF690005)
internal val DarkErrorContainer = Color(0xFF93000A)
internal val DarkOnErrorContainer = Color(0xFFFFDAD6)
internal val DarkBackground = Color(0xFF121318)
internal val DarkOnBackground = Color(0xFFE3E1E9)
internal val DarkSurface = Color(0xFF121318)
internal val DarkOnSurface = Color(0xFFE3E1E9)
internal val DarkSurfaceVariant = Color(0xFF45464F)
internal val DarkOnSurfaceVariant = Color(0xFFC6C5D0)
internal val DarkOutline = Color(0xFF90909A)

internal val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,
    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
)

internal val DarkColors = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline,
)
