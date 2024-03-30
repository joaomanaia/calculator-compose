package core.util

import androidx.compose.ui.text.input.TextFieldValue
import core.ButtonAction

interface ExpressionUtil {
    fun addParentheses(currentExpression: TextFieldValue): TextFieldValue

    fun calculateExpression(expression: String): String

    fun removeDigit(expression: TextFieldValue): TextFieldValue

    fun addActionValueToExpression(
        action: ButtonAction,
        currentExpression: TextFieldValue
    ): TextFieldValue

    fun changeAngleMode(newMode: model.AngleType)
}
