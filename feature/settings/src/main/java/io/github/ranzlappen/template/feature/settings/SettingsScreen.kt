package io.github.ranzlappen.template.feature.settings

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.ranzlappen.template.core.designsystem.component.ScreenContainer
import io.github.ranzlappen.template.core.designsystem.component.SectionHeader
import io.github.ranzlappen.template.core.designsystem.component.TemplateCard
import io.github.ranzlappen.template.core.model.DarkThemeConfig

/** Links shown in the About section. Adjust when the template is customized. */
private const val REPOSITORY_URL = "https://github.com/Ranzlappen/Kotlin"
private const val SUPPORT_URL = "https://ko-fi.com/F1F1140LWT"

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreenContent(
        uiState = uiState,
        onDarkThemeConfigChange = viewModel::setDarkThemeConfig,
        onUseDynamicColorChange = viewModel::setUseDynamicColor,
        modifier = modifier,
    )
}

@Composable
internal fun SettingsScreenContent(
    uiState: SettingsUiState,
    onDarkThemeConfigChange: (DarkThemeConfig) -> Unit,
    onUseDynamicColorChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    ScreenContainer(
        title = stringResource(R.string.settings_title),
        modifier = modifier,
    ) {
        if (uiState is SettingsUiState.Success) {
            SectionHeader(stringResource(R.string.settings_section_appearance))
            TemplateCard {
                DarkThemeConfig.entries.forEach { config ->
                    ThemeOptionRow(
                        config = config,
                        selected = uiState.preferences.darkThemeConfig == config,
                        onSelect = { onDarkThemeConfigChange(config) },
                    )
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.settings_dynamic_color),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f),
                        )
                        Switch(
                            checked = uiState.preferences.useDynamicColor,
                            onCheckedChange = onUseDynamicColorChange,
                            modifier = Modifier.semantics {
                                contentDescription = "Dynamic color"
                            },
                        )
                    }
                }
            }

            SectionHeader(stringResource(R.string.settings_section_language))
            TemplateCard {
                val currentLanguage = AppLanguage.current()
                AppLanguage.entries.forEach { language ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = currentLanguage == language,
                            onClick = { language.apply() },
                        )
                        Text(
                            text = stringResource(
                                when (language) {
                                    AppLanguage.SYSTEM -> R.string.settings_language_system
                                    AppLanguage.ENGLISH -> R.string.settings_language_english
                                    AppLanguage.GERMAN -> R.string.settings_language_german
                                },
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }

            SectionHeader(stringResource(R.string.settings_section_about))
            TemplateCard {
                Text(
                    text = stringResource(
                        R.string.settings_about_version,
                        context.appVersionName(),
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                TextButton(onClick = { context.openUrl(REPOSITORY_URL) }) {
                    Text(stringResource(R.string.settings_about_source))
                }
                TextButton(onClick = { context.openUrl(SUPPORT_URL) }) {
                    Text(stringResource(R.string.settings_about_support))
                }
            }
        }
    }
}

@Composable
private fun ThemeOptionRow(
    config: DarkThemeConfig,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = onSelect)
        Text(
            text = stringResource(
                when (config) {
                    DarkThemeConfig.FOLLOW_SYSTEM -> R.string.settings_theme_system
                    DarkThemeConfig.LIGHT -> R.string.settings_theme_light
                    DarkThemeConfig.DARK -> R.string.settings_theme_dark
                },
            ),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

private fun Context.openUrl(url: String) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(this, R.string.settings_no_browser, Toast.LENGTH_SHORT).show()
    }
}

private fun Context.appVersionName(): String = try {
    packageManager.getPackageInfo(packageName, 0).versionName ?: "?"
} catch (_: Exception) {
    "?"
}
