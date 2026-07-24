package io.github.ranzlappen.template.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.ranzlappen.template.R
import io.github.ranzlappen.template.feature.feedback.navigation.FEEDBACK_ROUTE
import io.github.ranzlappen.template.feature.home.navigation.HOME_ROUTE
import io.github.ranzlappen.template.feature.manual.navigation.MANUAL_ROUTE
import io.github.ranzlappen.template.feature.settings.navigation.SETTINGS_ROUTE

/** Top-level destinations shown in the adaptive navigation suite. */
enum class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @param:StringRes val labelRes: Int,
) {
    HOME(
        route = HOME_ROUTE,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        labelRes = R.string.nav_home,
    ),
    MANUAL(
        route = MANUAL_ROUTE,
        selectedIcon = Icons.AutoMirrored.Filled.MenuBook,
        unselectedIcon = Icons.AutoMirrored.Outlined.MenuBook,
        labelRes = R.string.nav_manual,
    ),
    FEEDBACK(
        route = FEEDBACK_ROUTE,
        selectedIcon = Icons.Filled.BugReport,
        unselectedIcon = Icons.Outlined.BugReport,
        labelRes = R.string.nav_feedback,
    ),
    SETTINGS(
        route = SETTINGS_ROUTE,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        labelRes = R.string.nav_settings,
    ),
}
