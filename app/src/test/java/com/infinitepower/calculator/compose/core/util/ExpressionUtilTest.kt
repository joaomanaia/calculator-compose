package com.infinitepower.calculator.compose.core.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.infinitepower.calculator.compose.core.evaluator.Expressions
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExpressionUtilTest {
    private lateinit var expressionUtil: ExpressionUtil

    @Before
    fun setup() {
        val expressions = Expressions()
        expressionUtil = ExpressionUtilImpl(expressions)
    }

    private fun basicTextField(
        text: String,
        range: IntRange = text.length..text.length
    ) = TextFieldValue(
        text = text,
        selection = TextRange(range.first, range.last)
    )

    @Test
    fun `correct add parentheses test`() {
        val expression1 = basicTextField("")
        val newExpression1 = expressionUtil.addParentheses(expression1)
        assertEquals("(", newExpression1)

        val expression2 = basicTextField("1+2*3")
        val newExpression2 = expressionUtil.addParentheses(expression2)
        assertEquals("1+2*3(", newExpression2)

        val expression3 = basicTextField("1+2*3(")
        val newExpression3 = expressionUtil.addParentheses(expression3)
        assertEquals("1+2*3((", newExpression3)

        val expression4 = basicTextField("1+2*3((5")
        val newExpression4 = expressionUtil.addParentheses(expression4)
        assertEquals("1+2*3((5)", newExpression4)

        val expression5 = basicTextField("1+2*3((5)")
        val newExpression5 = expressionUtil.addParentheses(expression5)
        assertEquals("1+2*3((5))", newExpression5)
    }

    @Test
    fun `calculate expression test`() {
        val expression1 = ""
        val result1 = expressionUtil.calculateExpression(expression1)
        assertEquals("", result1)

        val expression2 = "1+2*3"
        val result2 = expressionUtil.calculateExpression(expression2)
        assertEquals("7", result2)

        val expression3 = "1+2*3*((5))"
        val result3 = expressionUtil.calculateExpression(expression3)
        assertEquals("31", result3)
    }

    @Test
    fun `remove digit test`() {
        val expression1 = basicTextField("")
        val result1 = expressionUtil.removeDigit(expression1)
        assertEquals("", result1)

        val expression2 = basicTextField("1+2*3")
        val result2 = expressionUtil.removeDigit(expression2)
        assertEquals("1+2*", result2)

        val expression3 = basicTextField(
            text = "1+2*3",
            range = 0..2
        )
        val result3 = expressionUtil.removeDigit(expression3)
        assertEquals("2*3", result3)

        val expression4 = basicTextField(
            text = "1+2+3+4+5+6+7+8+9+10",
            range = 4..9
        )
        val result4 = expressionUtil.removeDigit(expression4)
        assertEquals("1+2++6+7+8+9+10", result4)
    }

    @Test
    fun `add actions to expression test`() {
        val expression1 = basicTextField("")
        val newExpression1 = expressionUtil.addActionValueToExpression(ButtonAction.Button0, expression1)
        assertEquals("0", newExpression1)

        val expression2 = basicTextField("(")
        val newExpression2 = expressionUtil.addActionValueToExpression(ButtonAction.Button0, expression2)
        assertEquals("(0", newExpression2)

        val expression3 = basicTextField("2+4")
        val newExpression3 = expressionUtil.addActionValueToExpression(ButtonAction.ButtonPlus, expression3)
        assertEquals("2+4+", newExpression3)
    }
}