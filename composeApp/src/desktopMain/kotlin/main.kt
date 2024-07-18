import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.KoinStarter
import org.koin.logger.SLF4JLogger

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun main() = application {
    System.setProperty(org.slf4j.simple.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "TRACE")

    KoinStarter.init {
        logger(SLF4JLogger())
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Calculator"
    ) {
        App(
            windowSizeClass = calculateWindowSizeClass()
        )
    }
}
