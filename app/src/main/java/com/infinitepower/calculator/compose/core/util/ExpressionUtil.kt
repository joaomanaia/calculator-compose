package com.infinitepower.calculator.compose.core.util

import androidx.compose.ui.text.input.TextFieldValue
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction

interface ExpressionUtil {
    fun addParentheses(currentExpression: TextFieldValue): String

    fun calculateExpression(expression: String): String

    fun removeDigit(expression: TextFieldValue): String

    fun addActionValueToExpression(
        action: ButtonAction,
        currentExpression: TextFieldValue
    ): String
}