package io.github.ranzlappen.template.core.data.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.github.ranzlappen.template.core.model.DarkThemeConfig
import io.github.ranzlappen.template.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreUserPreferencesRepository
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : UserPreferencesRepository {
        private object Keys {
            val DARK_THEME_CONFIG = stringPreferencesKey("dark_theme_config")
            val USE_DYNAMIC_COLOR = booleanPreferencesKey("use_dynamic_color")
        }

        override val userPreferences: Flow<UserPreferences> =
            dataStore.data.map { prefs ->
                UserPreferences(
                    darkThemeConfig =
                        prefs[Keys.DARK_THEME_CONFIG]
                            ?.let { stored -> DarkThemeConfig.entries.firstOrNull { it.name == stored } }
                            ?: DarkThemeConfig.FOLLOW_SYSTEM,
                    useDynamicColor = prefs[Keys.USE_DYNAMIC_COLOR] ?: true,
                )
            }

        override suspend fun setDarkThemeConfig(config: DarkThemeConfig) {
            dataStore.edit { it[Keys.DARK_THEME_CONFIG] = config.name }
        }

        override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
            dataStore.edit { it[Keys.USE_DYNAMIC_COLOR] = useDynamicColor }
        }
    }
