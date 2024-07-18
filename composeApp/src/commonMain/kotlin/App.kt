import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.presentation.theme.CalculatorTheme
import org.koin.compose.KoinContext
import presentation.home.HomeScreen

@Composable
fun App(
    windowSizeClass: WindowSizeClass
) {
    CalculatorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            KoinContext {
                HomeScreen(
                    windowSizeClass = windowSizeClass
                )
            }
        }
    }
}
