package me.joaomanaia.calculator.compose.core.util

import androidx.compose.ui.text.input.TextFieldValue
import me.joaomanaia.calculator.compose.core.AngleType
import me.joaomanaia.calculator.compose.ui.components.button.ButtonAction

interface ExpressionUtil {
    fun addParentheses(currentExpression: TextFieldValue): TextFieldValue

    fun calculateExpression(expression: String): String

    fun removeDigit(expression: TextFieldValue): TextFieldValue

    fun addActionValueToExpression(
        action: ButtonAction,
        currentExpression: TextFieldValue
    ): TextFieldValue

    fun changeAngleMode(newMode: AngleType)
}