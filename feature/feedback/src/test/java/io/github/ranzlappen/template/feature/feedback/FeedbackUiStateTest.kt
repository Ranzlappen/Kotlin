package io.github.ranzlappen.template.feature.feedback

import io.github.ranzlappen.template.core.model.FeedbackType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FeedbackUiStateTest {

    @Test
    fun `cannot submit with blank description`() {
        assertFalse(FeedbackUiState(description = "   ").canSubmit)
        assertFalse(FeedbackUiState(description = "").canSubmit)
    }

    @Test
    fun `can submit once a description exists`() {
        assertTrue(FeedbackUiState(description = "It crashes.").canSubmit)
    }

    @Test
    fun `defaults to bug report with device details attached`() {
        val state = FeedbackUiState()
        assertEquals(FeedbackType.BUG_REPORT, state.type)
        assertTrue(state.includeDeviceDetails)
    }
}
