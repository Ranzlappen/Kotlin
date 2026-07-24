package io.github.ranzlappen.template.core.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FeedbackReportTest {

    @Test
    fun `bug report renders title, description and environment table`() {
        val report = FeedbackReport(
            type = FeedbackType.BUG_REPORT,
            title = "Crash on launch",
            description = "The app crashes when rotating the screen.",
            deviceDetails = DeviceDetails(
                entries = listOf(
                    "Model" to "Pixel 8",
                    "Android" to "16 (SDK 36)",
                ),
            ),
        )

        val markdown = report.toMarkdown()

        assertEquals(
            """
            ## Bug report

            **Crash on launch**

            The app crashes when rotating the screen.

            ## Environment

            | Property | Value |
            |---|---|
            | Model | Pixel 8 |
            | Android | 16 (SDK 36) |
            """.trimIndent() + "\n",
            markdown,
        )
    }

    @Test
    fun `feature request without device details omits environment section`() {
        val report = FeedbackReport(
            type = FeedbackType.FEATURE_REQUEST,
            title = "",
            description = "Please add a widget.",
            deviceDetails = null,
        )

        val markdown = report.toMarkdown()

        assertTrue(markdown.startsWith("## Feature request"))
        assertFalse(markdown.contains("## Environment"))
        assertTrue(markdown.contains("Please add a widget."))
    }

    @Test
    fun `blank fields are skipped without leaving empty markdown`() {
        val markdown = FeedbackReport(
            type = FeedbackType.BUG_REPORT,
            title = "   ",
            description = "",
            deviceDetails = null,
        ).toMarkdown()

        assertEquals("## Bug report\n", markdown)
    }
}
