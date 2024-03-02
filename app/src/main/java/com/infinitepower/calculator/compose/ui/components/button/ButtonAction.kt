package com.infinitepower.calculator.compose.ui.components.button

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

sealed class ButtonAction(
    val value: String
) {
    companion object {
        private val colorSurface: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.surface

        private val colorPrimary: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)

        private val colorPrimaryContainer: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.primaryContainer

        private val colorTertiaryContainer: Color
            @Composable
            @ReadOnlyComposable
            get() = MaterialTheme.colorScheme.tertiaryContainer

        @Composable
        @ReadOnlyComposable
        fun ButtonAction.getColorByButton(): Color = when (this) {
            is Button0, Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, ButtonDot, ButtonRemove, ButtonSquareRoot, ButtonPI, ButtonPower, ButtonFactorial, ButtonSin, ButtonCos, ButtonTan, ButtonLog, ButtonLn, ButtonExp, -> colorSurface
            is ButtonParentheses, ButtonPercent, ButtonDivide, ButtonMultiply, ButtonMinus, ButtonPlus -> colorPrimary
            is ButtonEqual -> colorPrimaryContainer
            is ButtonClear -> colorTertiaryContainer
        }

        fun getAllPrimaryButtons(
            isPortrait: Boolean
        ): List<ButtonAction> = if (isPortrait) {
            listOf(
                ButtonClear,
                ButtonParentheses,
                ButtonPercent,
                ButtonDivide,
                Button7,
                Button8,
                Button9,
                ButtonMultiply,
                Button4,
                Button5,
                Button6,
                ButtonMinus,
                Button1,
                Button2,
                Button3,
                ButtonPlus,
                Button0,
                ButtonDot,
                ButtonRemove,
                ButtonEqual
            )
        } else {
            listOf(
                Button7,
                Button8,
                Button9,
                ButtonDivide,
                ButtonClear,
                Button4,
                Button5,
                Button6,
                ButtonMultiply,
                ButtonParentheses,
                Button1,
                Button2,
                Button3,
                ButtonMinus,
                ButtonPercent,
                Button0,
                ButtonDot,
                ButtonRemove,
                ButtonPlus,
                ButtonEqual
            )
        }

        fun getAllSecondaryButtons(): List<ButtonAction> = listOf(
            ButtonSquareRoot,
            ButtonPI,
            ButtonPower,
            ButtonFactorial,
            ButtonLog,
            ButtonLn,
            ButtonExp,
        )
    }

    object Button0 : ButtonAction(value = "0")
    object Button1 : ButtonAction(value = "1")
    object Button2 : ButtonAction(value = "2")
    object Button3 : ButtonAction(value = "3")
    object Button4 : ButtonAction(value = "4")
    object Button5 : ButtonAction(value = "5")
    object Button6 : ButtonAction(value = "6")
    object Button7 : ButtonAction(value = "7")
    object Button8 : ButtonAction(value = "8")
    object Button9 : ButtonAction(value = "9")

    object ButtonDot : ButtonAction(value = ".")
    object ButtonEqual : ButtonAction(value = "=")
    object ButtonPlus : ButtonAction(value = "+")
    object ButtonMinus : ButtonAction(value = "-")
    object ButtonMultiply : ButtonAction(value = "*")
    object ButtonDivide : ButtonAction(value = "/")
    object ButtonPercent : ButtonAction(value = "%")

    object ButtonClear : ButtonAction(value = "AC")
    object ButtonRemove : ButtonAction(value = "⌫")
    object ButtonParentheses : ButtonAction(value = "( )")

    object ButtonSquareRoot : ButtonAction(value = "√")
    object ButtonPI : ButtonAction(value = "π")
    object ButtonPower : ButtonAction(value = "^")
    object ButtonFactorial : ButtonAction(value = "!")
    object ButtonSin : ButtonAction(value = "sin")
    object ButtonCos : ButtonAction(value = "cos")
    object ButtonTan : ButtonAction(value = "tan")
    object ButtonLog : ButtonAction(value = "log")
    object ButtonLn : ButtonAction(value = "ln")
    object ButtonExp : ButtonAction(value = "e")
}
