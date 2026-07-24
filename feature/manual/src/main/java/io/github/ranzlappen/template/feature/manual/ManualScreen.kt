package io.github.ranzlappen.template.feature.manual

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.ranzlappen.template.core.designsystem.component.ScreenContainer
import io.github.ranzlappen.template.core.designsystem.component.TemplateCard
import io.github.ranzlappen.template.core.designsystem.theme.LocalSpacing

/**
 * One manual chapter: a heading plus its localized body text. The same
 * content must stay in sync with the wiki and the GitHub Pages user manual
 * (see LASTENHEFT.md — "user manual consistent across surfaces").
 */
private data class ManualChapter(
    @param:StringRes val title: Int,
    @param:StringRes val body: Int,
)

private val chapters = listOf(
    ManualChapter(R.string.manual_chapter_start_title, R.string.manual_chapter_start_body),
    ManualChapter(R.string.manual_chapter_theme_title, R.string.manual_chapter_theme_body),
    ManualChapter(R.string.manual_chapter_language_title, R.string.manual_chapter_language_body),
    ManualChapter(R.string.manual_chapter_feedback_title, R.string.manual_chapter_feedback_body),
    ManualChapter(R.string.manual_chapter_privacy_title, R.string.manual_chapter_privacy_body),
)

/** In-app user manual: expandable chapters, fully localized. */
@Composable
fun ManualScreen(modifier: Modifier = Modifier) {
    ScreenContainer(
        title = stringResource(R.string.manual_title),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.manual_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        chapters.forEach { chapter ->
            ManualChapterCard(chapter)
        }
    }
}

@Composable
private fun ManualChapterCard(chapter: ManualChapter) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val spacing = LocalSpacing.current
    TemplateCard(
        modifier = Modifier
            .clickable { expanded = !expanded }
            .animateContentSize(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(chapter.title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = null,
            )
        }
        if (expanded) {
            Text(
                text = stringResource(chapter.body),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = spacing.small),
            )
        }
    }
}
