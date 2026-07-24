package io.github.ranzlappen.template.feature.settings

import app.cash.turbine.test
import io.github.ranzlappen.template.core.data.prefs.UserPreferencesRepository
import io.github.ranzlappen.template.core.model.DarkThemeConfig
import io.github.ranzlappen.template.core.model.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

private class FakeUserPreferencesRepository : UserPreferencesRepository {
    private val state = MutableStateFlow(UserPreferences())

    override val userPreferences: Flow<UserPreferences> = state

    override suspend fun setDarkThemeConfig(config: DarkThemeConfig) {
        state.update { it.copy(darkThemeConfig = config) }
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        state.update { it.copy(useDynamicColor = useDynamicColor) }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `starts loading then emits preferences`() = runTest {
        val viewModel = SettingsViewModel(FakeUserPreferencesRepository())

        viewModel.uiState.test {
            assertEquals(SettingsUiState.Loading, awaitItem())
            assertEquals(
                SettingsUiState.Success(UserPreferences()),
                awaitItem(),
            )
        }
    }

    @Test
    fun `setting dark theme updates state`() = runTest {
        val viewModel = SettingsViewModel(FakeUserPreferencesRepository())

        viewModel.uiState.test {
            awaitItem() // Loading
            awaitItem() // initial Success
            viewModel.setDarkThemeConfig(DarkThemeConfig.DARK)
            assertEquals(
                SettingsUiState.Success(UserPreferences(darkThemeConfig = DarkThemeConfig.DARK)),
                awaitItem(),
            )
        }
    }

    @Test
    fun `disabling dynamic color updates state`() = runTest {
        val viewModel = SettingsViewModel(FakeUserPreferencesRepository())

        viewModel.uiState.test {
            awaitItem() // Loading
            awaitItem() // initial Success
            viewModel.setUseDynamicColor(false)
            assertEquals(
                SettingsUiState.Success(UserPreferences(useDynamicColor = false)),
                awaitItem(),
            )
        }
    }
}
