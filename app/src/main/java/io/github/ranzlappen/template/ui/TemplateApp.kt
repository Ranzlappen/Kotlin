package io.github.ranzlappen.template.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import io.github.ranzlappen.template.feature.feedback.navigation.feedbackScreen
import io.github.ranzlappen.template.feature.feedback.navigation.navigateToFeedback
import io.github.ranzlappen.template.feature.home.navigation.HOME_ROUTE
import io.github.ranzlappen.template.feature.home.navigation.homeScreen
import io.github.ranzlappen.template.feature.manual.navigation.manualScreen
import io.github.ranzlappen.template.feature.manual.navigation.navigateToManual
import io.github.ranzlappen.template.feature.settings.navigation.navigateToSettings
import io.github.ranzlappen.template.feature.settings.navigation.settingsScreen
import io.github.ranzlappen.template.navigation.TopLevelDestination

/**
 * App shell: adaptive navigation (bottom bar on phones, rail on tablets and
 * foldables — handled by [NavigationSuiteScaffold]) around the nav graph.
 */
@Composable
fun TemplateApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    fun navigateToTopLevel(destination: TopLevelDestination) {
        navController.navigate(
            destination.route,
            navOptions {
                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            },
        )
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestination.entries.forEach { destination ->
                val selected =
                    currentDestination
                        ?.hierarchy
                        ?.any { it.route == destination.route } == true
                item(
                    selected = selected,
                    onClick = { navigateToTopLevel(destination) },
                    icon = {
                        Icon(
                            imageVector =
                                if (selected) {
                                    destination.selectedIcon
                                } else {
                                    destination.unselectedIcon
                                },
                            contentDescription = null,
                        )
                    },
                    label = { Text(stringResource(destination.labelRes)) },
                )
            }
        },
    ) {
        Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HOME_ROUTE,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
            ) {
                homeScreen(
                    onNavigateToManual = navController::navigateToManual,
                    onNavigateToFeedback = navController::navigateToFeedback,
                    onNavigateToSettings = navController::navigateToSettings,
                )
                manualScreen()
                feedbackScreen()
                settingsScreen()
            }
        }
    }
}
