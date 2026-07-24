package io.github.ranzlappen.template.core.model

/** How the app resolves dark mode. */
enum class DarkThemeConfig {
    FOLLOW_SYSTEM,
    LIGHT,
    DARK,
}

/** User-editable app settings, persisted by `core:data`. */
data class UserPreferences(
    val darkThemeConfig: DarkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
    val useDynamicColor: Boolean = true,
)
