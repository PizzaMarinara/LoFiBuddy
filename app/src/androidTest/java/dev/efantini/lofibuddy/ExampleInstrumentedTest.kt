package dev.efantini.lofibuddy

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import dev.efantini.lofibuddy.presentation.MainActivity
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
class ExampleInstrumentedTest {

    @get:Rule()
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun mainTextIsDisplayed() {
        composeTestRule.onNodeWithText("Hello Sample Template!").assertExists()
    }
}
