package io.github.ranzlappen.template.feature.feedback

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.ranzlappen.template.core.designsystem.component.ScreenContainer
import io.github.ranzlappen.template.core.designsystem.component.SectionHeader
import io.github.ranzlappen.template.core.designsystem.component.TemplateCard
import io.github.ranzlappen.template.core.model.FeedbackType

// Feedback destinations. Adjust when the template is customized (the
// customizer script rewrites the repository URL).
private const val ISSUES_URL = "https://github.com/Ranzlappen/Kotlin/issues/new"
private const val FEEDBACK_EMAIL = "info@ranzlappen.com"

/**
 * Bug report / feature request flow, ported from the HardwareDash legacy
 * screen: the user writes a description, the app attaches a coarse device
 * snapshot, and the result is submitted as a prefilled GitHub issue, an
 * email, or a clipboard copy — no network code in the app itself.
 */
@Composable
fun FeedbackScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedbackViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ScreenContainer(
        title = stringResource(R.string.feedback_title),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.feedback_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            FeedbackType.entries.forEachIndexed { index, type ->
                SegmentedButton(
                    selected = uiState.type == type,
                    onClick = { viewModel.setType(type) },
                    shape =
                        SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = FeedbackType.entries.size,
                        ),
                ) {
                    Text(
                        stringResource(
                            when (type) {
                                FeedbackType.BUG_REPORT -> R.string.feedback_type_bug
                                FeedbackType.FEATURE_REQUEST -> R.string.feedback_type_feature
                            },
                        ),
                    )
                }
            }
        }

        OutlinedTextField(
            value = uiState.title,
            onValueChange = viewModel::setTitle,
            label = { Text(stringResource(R.string.feedback_field_title)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = uiState.description,
            onValueChange = viewModel::setDescription,
            label = { Text(stringResource(R.string.feedback_field_description)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp),
        )

        TemplateCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.feedback_include_device),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                )
                Switch(
                    checked = uiState.includeDeviceDetails,
                    onCheckedChange = viewModel::setIncludeDeviceDetails,
                )
            }
            Text(
                text = stringResource(R.string.feedback_include_device_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        SectionHeader(stringResource(R.string.feedback_submit_section))

        Button(
            onClick = { context.openGitHubIssue(uiState, viewModel.buildMarkdownReport()) },
            enabled = uiState.canSubmit,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.feedback_submit_github))
        }
        OutlinedButton(
            onClick = { context.sendFeedbackEmail(uiState, viewModel.buildMarkdownReport()) },
            enabled = uiState.canSubmit,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.feedback_submit_email))
        }
        OutlinedButton(
            onClick = { context.copyReport(viewModel.buildMarkdownReport()) },
            enabled = uiState.canSubmit,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.feedback_submit_copy))
        }
    }
}

private fun Context.openGitHubIssue(
    state: FeedbackUiState,
    markdown: String,
) {
    val url =
        ISSUES_URL +
            "?title=" + Uri.encode(state.title.ifBlank { defaultTitle(state) }) +
            "&labels=" + Uri.encode(if (state.type == FeedbackType.BUG_REPORT) "bug" else "enhancement") +
            "&body=" + Uri.encode(markdown)
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(this, R.string.feedback_no_handler, Toast.LENGTH_SHORT).show()
    }
}

private fun Context.sendFeedbackEmail(
    state: FeedbackUiState,
    markdown: String,
) {
    val mailto =
        Uri.parse(
            "mailto:$FEEDBACK_EMAIL" +
                "?subject=" + Uri.encode(state.title.ifBlank { defaultTitle(state) }) +
                "&body=" + Uri.encode(markdown),
        )
    try {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SENDTO, mailto),
                getString(R.string.feedback_submit_email),
            ),
        )
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(this, R.string.feedback_no_handler, Toast.LENGTH_SHORT).show()
    }
}

private fun Context.copyReport(markdown: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("Feedback report", markdown))
    Toast.makeText(this, R.string.feedback_copied, Toast.LENGTH_SHORT).show()
}

private fun Context.defaultTitle(state: FeedbackUiState): String =
    getString(
        when (state.type) {
            FeedbackType.BUG_REPORT -> R.string.feedback_type_bug
            FeedbackType.FEATURE_REQUEST -> R.string.feedback_type_feature
        },
    )
