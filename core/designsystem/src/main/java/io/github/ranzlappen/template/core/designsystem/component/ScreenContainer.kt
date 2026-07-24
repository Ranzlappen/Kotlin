package io.github.ranzlappen.template.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.ranzlappen.template.core.designsystem.theme.LocalSpacing

/**
 * Standard scrollable screen container: constrains content width on large
 * screens (tablet/desktop/foldable) so text lines stay readable, applies the
 * design-system padding, and renders a headline. Every feature screen should
 * use this as its root — it is the responsiveness contract of the app.
 */
@Composable
fun ScreenContainer(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 640.dp)
                .fillMaxWidth()
                .padding(spacing.medium),
            verticalArrangement = Arrangement.spacedBy(spacing.medium),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            content()
        }
    }
}
