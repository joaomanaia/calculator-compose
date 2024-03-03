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

    data object Button0 : ButtonAction(value = "0")
    data object Button1 : ButtonAction(value = "1")
    data object Button2 : ButtonAction(value = "2")
    data object Button3 : ButtonAction(value = "3")
    data object Button4 : ButtonAction(value = "4")
    data object Button5 : ButtonAction(value = "5")
    data object Button6 : ButtonAction(value = "6")
    data object Button7 : ButtonAction(value = "7")
    data object Button8 : ButtonAction(value = "8")
    data object Button9 : ButtonAction(value = "9")

    data object ButtonDot : ButtonAction(value = ".")
    data object ButtonEqual : ButtonAction(value = "=")
    data object ButtonPlus : ButtonAction(value = "+")
    data object ButtonMinus : ButtonAction(value = "-")
    data object ButtonMultiply : ButtonAction(value = "*")
    data object ButtonDivide : ButtonAction(value = "/")
    data object ButtonPercent : ButtonAction(value = "%")

    data object ButtonClear : ButtonAction(value = "AC")
    data object ButtonRemove : ButtonAction(value = "⌫")
    data object ButtonParentheses : ButtonAction(value = "( )")

    data object ButtonSquareRoot : ButtonAction(value = "√")
    data object ButtonPI : ButtonAction(value = "π")
    data object ButtonPower : ButtonAction(value = "^")
    data object ButtonFactorial : ButtonAction(value = "!")
    data object ButtonSin : ButtonAction(value = "sin")
    data object ButtonCos : ButtonAction(value = "cos")
    data object ButtonTan : ButtonAction(value = "tan")
    data object ButtonLog : ButtonAction(value = "log")
    data object ButtonLn : ButtonAction(value = "ln")
    data object ButtonExp : ButtonAction(value = "e")
}
