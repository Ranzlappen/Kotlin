package io.github.ranzlappen.template

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.ranzlappen.template.core.designsystem.theme.TemplateTheme
import io.github.ranzlappen.template.core.model.DarkThemeConfig
import io.github.ranzlappen.template.ui.TemplateApp

// AppCompatActivity (not ComponentActivity) so per-app language selection
// via AppCompatDelegate persists on Android < 13.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MainActivityViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val systemDark = isSystemInDarkTheme()
            val (darkTheme, dynamicColor) = when (val state = uiState) {
                MainActivityUiState.Loading -> systemDark to true
                is MainActivityUiState.Success -> {
                    val prefs = state.preferences
                    val dark = when (prefs.darkThemeConfig) {
                        DarkThemeConfig.FOLLOW_SYSTEM -> systemDark
                        DarkThemeConfig.LIGHT -> false
                        DarkThemeConfig.DARK -> true
                    }
                    dark to prefs.useDynamicColor
                }
            }

            TemplateTheme(
                darkTheme = darkTheme,
                dynamicColor = dynamicColor,
            ) {
                TemplateApp()
            }
        }
    }
}
