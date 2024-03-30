import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.presentation.theme.CalculatorTheme
import di.evaluatorModule
import org.koin.compose.KoinApplication
import presentation.home.HomeScreen

@Composable
fun App() {
    KoinApplication(
        application = {
            modules(evaluatorModule)
        }
    ) {
        CalculatorTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                HomeScreen()
            }
        }
    }
}
