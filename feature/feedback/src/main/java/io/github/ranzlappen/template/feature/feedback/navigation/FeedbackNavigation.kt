package io.github.ranzlappen.template.feature.feedback.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.github.ranzlappen.template.feature.feedback.FeedbackScreen

const val FEEDBACK_ROUTE = "feedback"

fun NavController.navigateToFeedback(navOptions: NavOptions? = null) =
    navigate(FEEDBACK_ROUTE, navOptions)

fun NavGraphBuilder.feedbackScreen() {
    composable(route = FEEDBACK_ROUTE) {
        FeedbackScreen()
    }
}
