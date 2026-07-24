package io.github.ranzlappen.template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.ranzlappen.template.core.data.prefs.UserPreferencesRepository
import io.github.ranzlappen.template.core.model.UserPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState

    data class Success(
        val preferences: UserPreferences,
    ) : MainActivityUiState
}

@HiltViewModel
class MainActivityViewModel
    @Inject
    constructor(
        userPreferencesRepository: UserPreferencesRepository,
    ) : ViewModel() {
        val uiState: StateFlow<MainActivityUiState> =
            userPreferencesRepository.userPreferences
                .map<UserPreferences, MainActivityUiState>(MainActivityUiState::Success)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                    initialValue = MainActivityUiState.Loading,
                )
    }
