package io.github.ranzlappen.template.core.data.prefs

import io.github.ranzlappen.template.core.model.DarkThemeConfig
import io.github.ranzlappen.template.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow

/** Persisted user settings. Backed by Preferences DataStore in production. */
interface UserPreferencesRepository {
    val userPreferences: Flow<UserPreferences>

    suspend fun setDarkThemeConfig(config: DarkThemeConfig)

    suspend fun setUseDynamicColor(useDynamicColor: Boolean)
}
