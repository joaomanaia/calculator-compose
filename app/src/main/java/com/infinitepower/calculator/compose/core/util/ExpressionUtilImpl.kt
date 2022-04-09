package com.infinitepower.calculator.compose.core.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.infinitepower.calculator.compose.core.evaluator.Expressions
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction

class ExpressionUtilImpl(
    private val expressions: Expressions
) : ExpressionUtil {
    override fun addParentheses(
        currentExpression: TextFieldValue,
    ): String {
        val currentExpressionText = currentExpression.text
        val cursorPosition = currentExpression.selection.start - 1

        val openParenthesesNum = currentExpressionText.count { it == '(' }
        val closeParenthesesNum = currentExpressionText.count { it == ')' }

        val lastDigitNumberOrCloseParentheses = currentExpressionText
            .getOrNull(cursorPosition)
            ?.digitToIntOrNull() != null || currentExpressionText.getOrNull(cursorPosition) == ')'

        val newExpression = when {
            openParenthesesNum == (closeParenthesesNum + 1) && lastDigitNumberOrCloseParentheses -> ")"
            openParenthesesNum > closeParenthesesNum && lastDigitNumberOrCloseParentheses -> ")"
            else -> "("
        }

        return addItemToExpression(newExpression, currentExpression)
    }

    override fun calculateExpression(expression: String): String = try {
        expressions.eval(expression).toString()
    } catch (e: Throwable) {
        ""
    }

    /**
     * Removes last digit if last digit of expression has spaces remove this spaces too
     */
    override fun removeDigit(expression: TextFieldValue): String {
        if (expression.text.isBlank()) return ""

        val expressionSelection = expression.selection
        val selectionRange = expression.selection.toIntRange()

        val removeRange = if (expressionSelection.start == expressionSelection.end) {
            (expressionSelection.start - 1) until expressionSelection.start
        } else selectionRange

        return expression.text.removeRange(removeRange)
    }

    private fun TextRange.toIntRange() = (this.start until this.end)

    override fun addActionValueToExpression(
        action: ButtonAction,
        currentExpression: TextFieldValue
    ): String = addItemToExpression(action.value, currentExpression)

    private fun addItemToExpression(
        item: String,
        currentExpression: TextFieldValue
    ): String {
        val positionToAdd = currentExpression.selection.start

        return currentExpression.text.replaceRange(positionToAdd, positionToAdd, item)
    }
}