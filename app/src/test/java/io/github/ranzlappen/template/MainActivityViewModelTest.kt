package io.github.ranzlappen.template

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
    val state = MutableStateFlow(UserPreferences())

    override val userPreferences: Flow<UserPreferences> = state

    override suspend fun setDarkThemeConfig(config: DarkThemeConfig) {
        state.update { it.copy(darkThemeConfig = config) }
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        state.update { it.copy(useDynamicColor = useDynamicColor) }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

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
    fun `emits loading then the persisted preferences`() = runTest {
        val repository = FakeUserPreferencesRepository()
        repository.setDarkThemeConfig(DarkThemeConfig.DARK)

        val viewModel = MainActivityViewModel(repository)

        viewModel.uiState.test {
            assertEquals(MainActivityUiState.Loading, awaitItem())
            assertEquals(
                MainActivityUiState.Success(UserPreferences(darkThemeConfig = DarkThemeConfig.DARK)),
                awaitItem(),
            )
        }
    }
}
