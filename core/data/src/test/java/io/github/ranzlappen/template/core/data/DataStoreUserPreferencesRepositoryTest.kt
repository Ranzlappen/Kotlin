package io.github.ranzlappen.template.core.data

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import app.cash.turbine.test
import io.github.ranzlappen.template.core.data.prefs.DataStoreUserPreferencesRepository
import io.github.ranzlappen.template.core.model.DarkThemeConfig
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class DataStoreUserPreferencesRepositoryTest {

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private fun repository(scope: CoroutineScope): DataStoreUserPreferencesRepository {
        val dataStore = PreferenceDataStoreFactory.create(scope = scope) {
            File(tmpFolder.root, "test_user_preferences.preferences_pb")
        }
        return DataStoreUserPreferencesRepository(dataStore)
    }

    @Test
    fun `defaults are follow-system and dynamic color on`() = testScope.runTest {
        val repo = repository(backgroundScope)
        repo.userPreferences.test {
            val prefs = awaitItem()
            assertEquals(DarkThemeConfig.FOLLOW_SYSTEM, prefs.darkThemeConfig)
            assertTrue(prefs.useDynamicColor)
        }
    }

    @Test
    fun `set dark theme config is persisted and emitted`() = testScope.runTest {
        val repo = repository(backgroundScope)
        repo.setDarkThemeConfig(DarkThemeConfig.DARK)
        repo.userPreferences.test {
            assertEquals(DarkThemeConfig.DARK, awaitItem().darkThemeConfig)
        }
    }

    @Test
    fun `set dynamic color off is persisted and emitted`() = testScope.runTest {
        val repo = repository(backgroundScope)
        repo.setUseDynamicColor(false)
        repo.userPreferences.test {
            assertFalse(awaitItem().useDynamicColor)
        }
    }
}
