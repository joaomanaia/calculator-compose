package me.joaomanaia.calculator.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.joaomanaia.calculator.compose.ui.theme.CalculatorTheme
import dagger.hilt.android.AndroidEntryPoint
import me.joaomanaia.calculator.compose.ui.home.HomeScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                HomeScreen()
            }
        }
    }
}
