package dev.efantini.lofibuddy.presentation.shared

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import dev.efantini.lofibuddy.presentation.shared.navigation.BaseNavHost
import dev.efantini.lofibuddy.presentation.shared.theme.LoFiBuddyTheme

@ExperimentalMaterialApi
@Composable
fun MainContent() {
    LoFiBuddyTheme {
        BaseNavHost()
    }
}
