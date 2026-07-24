package io.github.ranzlappen.template.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.ranzlappen.template.core.designsystem.component.ScreenContainer
import io.github.ranzlappen.template.core.designsystem.component.SectionHeader
import io.github.ranzlappen.template.core.designsystem.component.TemplateCard
import io.github.ranzlappen.template.core.designsystem.theme.LocalSpacing

/**
 * Landing screen. In the template it introduces the project and links to the
 * other features; replace its content with your app's real start screen.
 */
@Composable
fun HomeScreen(
    onNavigateToManual: () -> Unit,
    onNavigateToFeedback: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ScreenContainer(
        title = stringResource(R.string.home_title),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.home_intro),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        SectionHeader(stringResource(R.string.home_explore))

        HomeLinkCard(
            icon = Icons.AutoMirrored.Filled.MenuBook,
            title = stringResource(R.string.home_card_manual_title),
            subtitle = stringResource(R.string.home_card_manual_subtitle),
            onClick = onNavigateToManual,
        )
        HomeLinkCard(
            icon = Icons.Filled.BugReport,
            title = stringResource(R.string.home_card_feedback_title),
            subtitle = stringResource(R.string.home_card_feedback_subtitle),
            onClick = onNavigateToFeedback,
        )
        HomeLinkCard(
            icon = Icons.Filled.Settings,
            title = stringResource(R.string.home_card_settings_title),
            subtitle = stringResource(R.string.home_card_settings_subtitle),
            onClick = onNavigateToSettings,
        )
    }
}

@Composable
private fun HomeLinkCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    TemplateCard(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp),
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
