package io.github.ranzlappen.template.core.model

/** What kind of feedback the user is filing. */
enum class FeedbackType {
    BUG_REPORT,
    FEATURE_REQUEST,
}

/**
 * Snapshot of the device and app the report was written on. Collected by the
 * feedback feature; kept as plain strings so this module stays Android-free.
 */
data class DeviceDetails(
    val entries: List<Pair<String, String>>,
)

/** A complete feedback report, ready to be rendered to Markdown. */
data class FeedbackReport(
    val type: FeedbackType,
    val title: String,
    val description: String,
    val deviceDetails: DeviceDetails?,
)

/**
 * Renders the report as GitHub-flavored Markdown. Pure and unit-tested;
 * the exact shape mirrors the issue templates in `.github/ISSUE_TEMPLATE`.
 */
fun FeedbackReport.toMarkdown(): String = buildString {
    appendLine("## ${if (type == FeedbackType.BUG_REPORT) "Bug report" else "Feature request"}")
    appendLine()
    if (title.isNotBlank()) {
        appendLine("**${title.trim()}**")
        appendLine()
    }
    if (description.isNotBlank()) {
        appendLine(description.trim())
        appendLine()
    }
    deviceDetails?.let { details ->
        appendLine("## Environment")
        appendLine()
        appendLine("| Property | Value |")
        appendLine("|---|---|")
        details.entries.forEach { (label, value) ->
            appendLine("| $label | $value |")
        }
    }
}.trimEnd() + "\n"
