package me.joaomanaia.calculator.compose

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import di.databaseModule
import di.evaluatorModule
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinApplication(
                application = {
                    androidContext(this@MainActivity)
                    modules(evaluatorModule, databaseModule)
                }
            ) {
                App()
            }
        }
    }
}
