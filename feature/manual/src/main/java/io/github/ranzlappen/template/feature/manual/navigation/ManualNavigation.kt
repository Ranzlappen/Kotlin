package io.github.ranzlappen.template.feature.manual.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.github.ranzlappen.template.feature.manual.ManualScreen

const val MANUAL_ROUTE = "manual"

fun NavController.navigateToManual(navOptions: NavOptions? = null) = navigate(MANUAL_ROUTE, navOptions)

fun NavGraphBuilder.manualScreen() {
    composable(route = MANUAL_ROUTE) {
        ManualScreen()
    }
}
