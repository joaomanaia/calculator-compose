package com.infinitepower.calculator.compose.core.util

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.infinitepower.calculator.compose.core.evaluator.Expressions
import com.infinitepower.calculator.compose.core.evaluator.internal.Evaluator
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.BeforeTest
import kotlin.test.Test

class ExpressionUtilTest {
    private lateinit var expressionUtil: ExpressionUtil

    @BeforeTest
    fun setup() {
        val expressions = Expressions(evaluator = Evaluator())
        expressionUtil = ExpressionUtilImpl(expressions)
    }

    private fun basicTextField(
        text: String,
        range: IntRange = text.length..text.length
    ) = TextFieldValue(
        text = text,
        selection = TextRange(range.first, range.last)
    )

    @ParameterizedTest(name = "add parentheses at cursor {1} to {2}: {0} -> {3}")
    @CsvSource(
        "'', 0, 0, (, 1",
        "1+2*3, 0, 0, (1+2*3, 1",
        "1+2*3, 1, 1, 1(+2*3, 2",
        "1+2*3, 5, 5, 1+2*3(, 6",
        "1+2*3(, 6, 6, 1+2*3((, 7",
        "1+2*3((5, 8, 8, 1+2*3((5), 9",
        "1+2*3((5), 9, 9, 1+2*3((5)), 10",
        "1+2*3, 0, 3, (1+2)*3, 5",
    )
    fun `correct add parentheses test`(
        expressionText: String,
        cursorPosStart: Int,
        cursorPosEnd: Int,
        expectedText: String,
        expectedCursorPos: Int
    ) {
        val expression = TextFieldValue(
            text = expressionText,
            selection = TextRange(cursorPosStart, cursorPosEnd)
        )

        val newExpression = expressionUtil.addParentheses(expression)

        assertThat(newExpression.text).isEqualTo(expectedText)
        assertThat(newExpression.selection).isEqualTo(TextRange(expectedCursorPos))
    }

    @ParameterizedTest
    @CsvSource(
        "'', ''",
        "1+2*3, 7",
        "1+2*3((5), ''", // If there is an open parenthesis without a close parenthesis, do not calculate
    )
    fun `calculate expression test`(
        expressionText: String,
        expected: String
    ) {
        val expression = expressionUtil.calculateExpression(expressionText)
        assertThat(expression).isEqualTo(expected)
    }

    @ParameterizedTest
    @CsvSource(
        "'', 0, 0, ''",
        "1+2*3, 0, 0, 1+2*3",
        "1+2*3, 5, 5, 1+2*",
        "1+2*3, 0, 3, *3",
        "1+2*3, 0, 5, ''",
        "2cos, 4, 4, 2", // When removing inside the function, remove the function too
        "2cos(, 3, 3, 2", // When removing inside the function, remove the function too
        "2cos(, 5, 5, 2", // When removing the parenthesis with function, remove the function too
        "2cos((, 6, 6, 2cos(",
        "2cos(, 2, 4, 2", // When selecting part of the function, remove all of it
        "2cos(2)+cos(3)*8cos(5), 9, 9, 2cos(2)+3)*8cos(5)", // Should remove the middle function and the parenthesis
    )
    fun `remove digit test`(
        expressionText: String,
        cursorPosStart: Int,
        cursorPosEnd: Int,
        expected: String
    ) {
        val expression = basicTextField(text = expressionText, range = cursorPosStart..cursorPosEnd)
        val newExpression = expressionUtil.removeDigit(expression)
        assertThat(newExpression.text).isEqualTo(expected)
    }

    @Test
    fun `add actions to expression test`() {
        val expression1 = basicTextField("")
        val newExpression1 = expressionUtil.addActionValueToExpression(ButtonAction.Button0, expression1)
        assertThat(newExpression1.text).isEqualTo("0")

        val expression2 = basicTextField("(")
        val newExpression2 = expressionUtil.addActionValueToExpression(ButtonAction.Button0, expression2)
        assertThat(newExpression2.text).isEqualTo("(0")

        val expression3 = basicTextField("2+4")
        val newExpression3 = expressionUtil.addActionValueToExpression(ButtonAction.ButtonPlus, expression3)
        assertThat(newExpression3.text).isEqualTo("2+4+")
    }
}