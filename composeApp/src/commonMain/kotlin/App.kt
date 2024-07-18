import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.presentation.theme.CalculatorTheme
import presentation.home.HomeScreen

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun App() {
    CalculatorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val windowSizeClass = calculateWindowSizeClass()

            HomeScreen(
                windowSizeClass = windowSizeClass
            )
        }
    }
}
