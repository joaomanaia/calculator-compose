import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.databaseModule
import di.evaluatorModule
import org.koin.compose.KoinApplication

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Calculator"
    ) {
        KoinApplication(
            application = {
                modules(evaluatorModule, databaseModule)
            }
        ) {
            App()
        }
    }
}
