package dev.efantini.lofibuddy.presentation.shared.navigation

sealed class NavigationItem(
    val route: String,
    val fullRoute: String
) {
    object ViewerScreen : NavigationItem(
        "viewerscreen", "viewerscreen"
    )
}
