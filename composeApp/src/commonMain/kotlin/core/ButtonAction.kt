package core

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

sealed class ButtonAction(
    val displayText: String,
    val value: String = displayText
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
            is ButtonParentheses, ButtonPercent, ButtonDivide, ButtonMultiply, ButtonMinus, ButtonPlus -> colorPrimary
            is ButtonEqual -> colorPrimaryContainer
            is ButtonClear -> colorTertiaryContainer
            else -> colorSurface
        }

        fun getAllPrimaryButtons(isPortrait: Boolean): List<ButtonAction> = listOf(
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

        fun getAllSecondaryButtons(
            angleType: model.AngleType,
            isInverse: Boolean
        ): List<ButtonAction> = listOf(
            ButtonSquareRoot(isInverse),
            ButtonPI,
            ButtonPower,
            ButtonFactorial,
            ButtonAngle(angleType),
            ButtonSin(isInverse),
            ButtonCos(isInverse),
            ButtonTan(isInverse),
            ButtonInverse,
            ButtonExp,
            ButtonLn,
            ButtonLog(isInverse)
        )
    }

    interface Invertible {
        val isInverted: Boolean
    }

    data object Button0 : ButtonAction(displayText = "0")
    data object Button1 : ButtonAction(displayText = "1")
    data object Button2 : ButtonAction(displayText = "2")
    data object Button3 : ButtonAction(displayText = "3")
    data object Button4 : ButtonAction(displayText = "4")
    data object Button5 : ButtonAction(displayText = "5")
    data object Button6 : ButtonAction(displayText = "6")
    data object Button7 : ButtonAction(displayText = "7")
    data object Button8 : ButtonAction(displayText = "8")
    data object Button9 : ButtonAction(displayText = "9")

    data object ButtonDot : ButtonAction(displayText = ".")
    data object ButtonEqual : ButtonAction(displayText = "=")
    data object ButtonPlus : ButtonAction(displayText = "+")
    data object ButtonMinus : ButtonAction(displayText = "-")
    data object ButtonMultiply : ButtonAction(displayText = "*")
    data object ButtonDivide : ButtonAction(displayText = "/")
    data object ButtonPercent : ButtonAction(displayText = "%")

    data object ButtonClear : ButtonAction(displayText = "AC")
    data object ButtonRemove : ButtonAction(displayText = "⌫")
    data object ButtonParentheses : ButtonAction(displayText = "( )")

    data class ButtonSquareRoot(
        override val isInverted: Boolean
    ) : ButtonAction(
        displayText = if (isInverted) "x²" else "√",
        value = if (isInverted) "^2" else "√"
    ), Invertible
    data object ButtonPI : ButtonAction(displayText = "π")
    data object ButtonPower : ButtonAction(displayText = "^")
    data object ButtonFactorial : ButtonAction(displayText = "!")

    data class ButtonAngle(
        val angleType: model.AngleType
    ) : ButtonAction(displayText = angleType.next().name)

    data object ButtonInverse : ButtonAction(displayText = "INV")

    data class ButtonSin(
        override val isInverted: Boolean,
    ) : ButtonAction(
        displayText = if (isInverted) "sin⁻¹" else "sin",
        value = if (isInverted) "asin(" else "sin("
    ), Invertible

    data class ButtonCos(
        override val isInverted: Boolean = false
    ) : ButtonAction(
        displayText = if (isInverted) "cos⁻¹" else "cos",
        value = if (isInverted) "acos(" else "cos("
    ), Invertible

    data class ButtonTan(
        override val isInverted: Boolean = false
    ) : ButtonAction(
        displayText = if (isInverted) "tan⁻¹" else "tan",
        value = if (isInverted) "atan(" else "tan("
    ), Invertible

    data class ButtonLog(
        override val isInverted: Boolean
    ) : ButtonAction(
        displayText = if (isInverted) "10^" else "log",
        value = if (isInverted) "10^" else "log("
    ), Invertible

    data object ButtonLn : ButtonAction(
        displayText = "ln",
        value = "ln("
    )

    data object ButtonExp : ButtonAction(displayText = "e")
}
