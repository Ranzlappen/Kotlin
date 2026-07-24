package io.github.ranzlappen.template.core.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import io.github.ranzlappen.template.core.designsystem.theme.LocalSpacing

/** Section heading with consistent style and accessibility semantics. */
@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier =
            modifier
                .padding(top = spacing.small)
                .semantics { heading() },
    )
}
