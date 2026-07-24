package io.github.ranzlappen.template.feature.feedback

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.ranzlappen.template.core.model.FeedbackReport
import io.github.ranzlappen.template.core.model.FeedbackType
import io.github.ranzlappen.template.core.model.toMarkdown
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class FeedbackUiState(
    val type: FeedbackType = FeedbackType.BUG_REPORT,
    val title: String = "",
    val description: String = "",
    val includeDeviceDetails: Boolean = true,
) {
    val canSubmit: Boolean get() = description.isNotBlank()
}

@HiltViewModel
class FeedbackViewModel
    @Inject
    constructor(
        private val deviceDetailsProvider: DeviceDetailsProvider,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FeedbackUiState())
        val uiState: StateFlow<FeedbackUiState> = _uiState.asStateFlow()

        fun setType(type: FeedbackType) = _uiState.update { it.copy(type = type) }

        fun setTitle(title: String) = _uiState.update { it.copy(title = title) }

        fun setDescription(description: String) = _uiState.update { it.copy(description = description) }

        fun setIncludeDeviceDetails(include: Boolean) = _uiState.update { it.copy(includeDeviceDetails = include) }

        /** Renders the current state as the Markdown report all submit paths share. */
        fun buildMarkdownReport(): String {
            val state = _uiState.value
            return FeedbackReport(
                type = state.type,
                title = state.title,
                description = state.description,
                deviceDetails = if (state.includeDeviceDetails) deviceDetailsProvider.collect() else null,
            ).toMarkdown()
        }
    }
