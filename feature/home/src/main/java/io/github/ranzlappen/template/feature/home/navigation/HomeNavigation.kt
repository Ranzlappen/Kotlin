package io.github.ranzlappen.template.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.github.ranzlappen.template.feature.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(
    onNavigateToManual: () -> Unit,
    onNavigateToFeedback: () -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeScreen(
            onNavigateToManual = onNavigateToManual,
            onNavigateToFeedback = onNavigateToFeedback,
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
