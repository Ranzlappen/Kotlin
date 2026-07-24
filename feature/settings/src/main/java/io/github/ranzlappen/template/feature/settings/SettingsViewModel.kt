package io.github.ranzlappen.template.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.ranzlappen.template.core.data.prefs.UserPreferencesRepository
import io.github.ranzlappen.template.core.model.DarkThemeConfig
import io.github.ranzlappen.template.core.model.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SettingsUiState {
    data object Loading : SettingsUiState

    data class Success(
        val preferences: UserPreferences,
    ) : SettingsUiState
}

@HiltViewModel
class SettingsViewModel
    @Inject
    constructor(
        private val userPreferencesRepository: UserPreferencesRepository,
    ) : ViewModel() {
        val uiState: StateFlow<SettingsUiState> =
            userPreferencesRepository.userPreferences
                .map<UserPreferences, SettingsUiState>(SettingsUiState::Success)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                    initialValue = SettingsUiState.Loading,
                )

        fun setDarkThemeConfig(config: DarkThemeConfig) {
            viewModelScope.launch { userPreferencesRepository.setDarkThemeConfig(config) }
        }

        fun setUseDynamicColor(useDynamicColor: Boolean) {
            viewModelScope.launch { userPreferencesRepository.setUseDynamicColor(useDynamicColor) }
        }
    }
