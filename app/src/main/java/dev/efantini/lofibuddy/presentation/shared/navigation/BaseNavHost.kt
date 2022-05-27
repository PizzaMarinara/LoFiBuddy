package dev.efantini.lofibuddy.presentation.shared.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.efantini.lofibuddy.presentation.viewerscreen.ViewerScreenContent

@ExperimentalMaterialApi
@Composable
fun BaseNavHost(
    startDestination: String = NavigationItem.ViewerScreen.route
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavigationItem.ViewerScreen.fullRoute) {
            ViewerScreenContent()
        }
    }
}
