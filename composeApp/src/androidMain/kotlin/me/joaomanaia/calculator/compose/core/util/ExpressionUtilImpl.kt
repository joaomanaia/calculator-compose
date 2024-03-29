package me.joaomanaia.calculator.compose.core.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import me.joaomanaia.calculator.compose.core.AngleType
import me.joaomanaia.calculator.compose.ui.components.button.ButtonAction
import me.joaomanaia.calculator.compose.core.evaluator.Expressions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ExpressionUtilImpl @Inject constructor(
    private val expressions: Expressions
) : ExpressionUtil {
    companion object {
        val angleFunctions = setOf("sin", "sin⁻¹", "cos", "cos⁻¹", "tan", "tan⁻¹")
        private val functions = angleFunctions + setOf("log", "ln")
    }

    override fun addParentheses(currentExpression: TextFieldValue): TextFieldValue {
        val currentExpressionText = currentExpression.text

        // If there is a selection, add parentheses around the selected text
        if (currentExpression.selection.length > 0) {
            // Get the selected text
            val selectedText = currentExpressionText.substring(
                currentExpression.selection.start,
                currentExpression.selection.end
            )

            return currentExpression.copy(
                // Replace selected text with parentheses around it
                text = currentExpression.text.replace(selectedText, "($selectedText)"),
                selection = TextRange(currentExpression.selection.end + 2)
            )
        }

        val openParenthesesNum = currentExpressionText.count { it == '(' }
        val closeParenthesesNum = currentExpressionText.count { it == ')' }

        val previousChar = currentExpressionText.getOrNull(currentExpression.selection.start - 1)

        // Check if previous character is a digit, close parentheses, e or π
        // If true, add close parentheses
        val shouldAddCloseParentheses = previousChar?.digitToIntOrNull() != null
                || previousChar == ')'
                || previousChar == 'e'
                || previousChar == 'π'

        val newExpression =
            if (openParenthesesNum > closeParenthesesNum && shouldAddCloseParentheses) ")" else "("

        val newCursorPosition = currentExpression.selection.start + 1

        return currentExpression.copy(
            text = addItemToExpression(newExpression, currentExpression),
            selection = TextRange(newCursorPosition)
        )
    }

    override fun calculateExpression(expression: String): String = try {
        expressions.evalToString(expression)
    } catch (e: Throwable) {
        ""
    }

    override fun removeDigit(expression: TextFieldValue): TextFieldValue {
        if (expression.text.isBlank()) return expression

        val selection = expression.selection

        // If there is no selection and the cursor is at the beginning of the expression, return the expression as is
        if (selection.collapsed && selection.start == 0) return expression

        val removeRange = if (selection.collapsed) {
            // If there is no selection, remove the character before the cursor
            selection.start - 1 until selection.start
        } else {
            // If there is a selection, remove the selected text
            selection.start until selection.end
        }

        val textSelected = expression.text.substring(removeRange)
        val textBeforeCursor = expression.text.substring(0, removeRange.first)

        functions.forEach { function ->
            if (textSelected in function || textBeforeCursor.endsWith(function)) {
                // Remove the function if the cursor is inside it
                val functionStartIndex = expression.text.lastIndexOf(function, removeRange.first)

                if (functionStartIndex != -1) {
                    // Check if the function has parentheses at the end
                    val hasParentheses =
                        expression.text.getOrNull(functionStartIndex + function.length) == '('
                    val endIndex =
                        if (hasParentheses) functionStartIndex + function.length + 1 else functionStartIndex + function.length

                    return expression.copy(
                        text = expression.text.removeRange(
                            startIndex = functionStartIndex,
                            endIndex = endIndex
                        ),
                        selection = TextRange(removeRange.first)
                    )
                }
            }
        }

        return expression.copy(
            text = expression.text.removeRange(removeRange),
            selection = TextRange(removeRange.first)
        )
    }

    override fun addActionValueToExpression(
        action: ButtonAction,
        currentExpression: TextFieldValue
    ): TextFieldValue {
        val actionValue = action.value

        return currentExpression.copy(
            text = addItemToExpression(actionValue, currentExpression),
            selection = TextRange(currentExpression.selection.start + actionValue.length)
        )
    }

    private fun addItemToExpression(
        item: String,
        currentExpression: TextFieldValue
    ): String {
        val positionToAdd = currentExpression.selection.start

        return currentExpression.text.replaceRange(positionToAdd, positionToAdd, item)
    }

    override fun changeAngleMode(newMode: AngleType) {
        expressions.changeAngleFunctions(newMode)
    }
}