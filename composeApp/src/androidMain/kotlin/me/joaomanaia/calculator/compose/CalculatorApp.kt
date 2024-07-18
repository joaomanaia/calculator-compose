package me.joaomanaia.calculator.compose

import android.app.Application
import di.KoinStarter
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class CalculatorApp : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinStarter.init {
            androidLogger()
            androidContext(this@CalculatorApp)
        }
    }
}
