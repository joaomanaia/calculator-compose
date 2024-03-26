package com.infinitepower.calculator.compose.ui.home

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.infinitepower.calculator.compose.core.util.ExpressionUtil
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expressionUtil: ExpressionUtil
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnButtonActionClick -> processAction(event.action)
            is HomeUiEvent.UpdateTextFieldValue -> updateTextFieldValue(event.value)
            is HomeUiEvent.OnChangeMoreActionsClick -> changeMoreActionsState()
        }
    }

    private fun processAction(action: ButtonAction) {
        when (action) {
            is ButtonAngle -> changeAngleType()
            is ButtonInverse -> changeInverse()
            is ButtonEqual -> replaceResultInExpression()
            is ButtonClear -> clearExpression()
            is ButtonParentheses -> addParentheses()
            is ButtonRemove -> removeDigit()
            Button0, Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, ButtonDot, ButtonPercent, ButtonDivide, ButtonMultiply, ButtonMinus, ButtonPlus, is ButtonSquareRoot, ButtonPI, ButtonPower, ButtonFactorial, is ButtonSin, is ButtonCos, is ButtonTan, is ButtonLog, ButtonLn, ButtonExp -> addActionValueToExpression(action)
        }
    }

    private fun changeAngleType() {
        _uiState.update { state ->
            state.copy(angleType = state.angleType.next())
        }
    }

    private fun changeInverse() {
        _uiState.update { state ->
            state.copy(isInverse = !state.isInverse)
        }
    }

    private fun addActionValueToExpression(action: ButtonAction) {
        _uiState.update { currentState ->
            val currentExpression = currentState.currentExpression
            val newExpression = expressionUtil.addActionValueToExpression(action, currentExpression)

            val newCursorPosition = when (action) {
                is ButtonLog, is ButtonLn, is ButtonSquareRoot -> currentExpression.selection.start + action.value.length + 1
                else -> currentExpression.selection.start + action.value.length
            }

            // Solve the new expression
            val result = expressionUtil.calculateExpression(newExpression)

            currentState.copy(
                currentExpression = TextFieldValue(
                    text = newExpression,
                    selection = TextRange(newCursorPosition, newCursorPosition)
                ),
                result = result
            )
        }
    }

    private fun clearExpression() {
        _uiState.update { currentState ->
            currentState.copy(
                currentExpression = TextFieldValue(""),
                result = ""
            )
        }
    }

    /**
     * Replace the current expression with the result and set the cursor at the end of the expression
     */
    private fun replaceResultInExpression() {
        _uiState.update { currentState ->
            currentState.copy(
                currentExpression = TextFieldValue(
                    text = currentState.result,
                    // Set the cursor at the end of the expression
                    selection = TextRange(currentState.result.length)
                )
            )
        }
    }

    private fun removeDigit() {
        _uiState.update { currentState ->
            val currentExpression = currentState.currentExpression
            val newExpression = expressionUtil.removeDigit(currentExpression)

            // Solve the new expression
            val result = expressionUtil.calculateExpression(newExpression.text)

            currentState.copy(
                currentExpression = newExpression,
                result = result
            )
        }
    }

    private fun addParentheses() {
        _uiState.update { uiState ->
            val currentExpression = uiState.currentExpression
            // val newCursorPosition = currentExpression.selection.start + 1

            val newExpression = expressionUtil.addParentheses(currentExpression)

            val result = expressionUtil.calculateExpression(newExpression.text)

            uiState.copy(
                currentExpression = newExpression,
                result = result
            )
        }
    }

    private fun updateTextFieldValue(value: TextFieldValue) {
        _uiState.update { state ->
            val result = expressionUtil.calculateExpression(value.text)

            state.copy(
                currentExpression = value,
                result = result
            )
        }
    }

    private fun changeMoreActionsState() {
        _uiState.update { state ->
            state.copy(moreActionsExpanded = !state.moreActionsExpanded)
        }
    }
}