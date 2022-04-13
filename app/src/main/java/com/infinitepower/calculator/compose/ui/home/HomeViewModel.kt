package com.infinitepower.calculator.compose.ui.home

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinitepower.calculator.compose.core.util.ExpressionUtil
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expressionUtil: ExpressionUtil
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeUiEvent) = viewModelScope.launch(Dispatchers.IO) {
        when (event) {
            is HomeUiEvent.OnButtonActionClick -> processAction(event.action)
            is HomeUiEvent.UpdateTextFieldValue -> updateTextFieldValue(event.value)
            is HomeUiEvent.OnChangeMoreActionsState -> changeMoreActionsState(event.expanded)
        }
    }

    private suspend fun processAction(action: ButtonAction) {
        when (action) {
            is Button0, Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, ButtonDot, ButtonPercent, ButtonDivide, ButtonMultiply, ButtonMinus, ButtonPlus, ButtonSquareRoot, ButtonPI, ButtonPower, ButtonFactorial, ButtonSin, ButtonCos, ButtonTan, ButtonLog, ButtonLn, ButtonExp -> addActionValueToExpression(action)
            is ButtonEqual -> addResultToExpression()
            is ButtonClear -> clearExpression()
            is ButtonParentheses -> addParentheses()
            is ButtonRemove -> removeDigit()
        }
    }

    private suspend fun addActionValueToExpression(
        action: ButtonAction
    ) {
        val currentExpression = uiState.first().currentExpression
        val newCursorPosition = when (action) {
            is ButtonLog, ButtonLn, ButtonSquareRoot -> currentExpression.selection.start + action.value.length + 1
            else -> currentExpression.selection.start + action.value.length
        }

        val newExpression = expressionUtil.addActionValueToExpression(action, currentExpression)
        calculateExpression(
            currentExpression.copy(
                text = newExpression,
                selection = TextRange(newCursorPosition, newCursorPosition)
            )
        )
    }

    private fun calculateExpression(
        newExpression: TextFieldValue
    ) {
        val result = expressionUtil.calculateExpression(newExpression.text)
        updateUiState(newExpression, result)
    }

    private fun updateUiState(
        currentExpression: TextFieldValue,
        result: String
    ) {
        _uiState.update { state ->
            state.copy(
                currentExpression = currentExpression,
                result = result
            )
        }
    }

    private fun clearExpression() {
        updateUiState(TextFieldValue(""), "")
    }

    private suspend fun addResultToExpression() {
        val currentUiState = uiState.first()
        updateUiState(TextFieldValue(currentUiState.result), currentUiState.result)
    }

    private suspend fun removeDigit() {
        val currentExpression = uiState.first().currentExpression

        val newExpression = expressionUtil.removeDigit(currentExpression)
        val newCursorPosition = currentExpression.selection.start - 1

        calculateExpression(
            currentExpression.copy(
                text = newExpression,
                selection = if (newCursorPosition < 0) TextRange.Zero else TextRange(newCursorPosition, newCursorPosition)
            )
        )
    }

    private suspend fun addParentheses() {
        val currentExpression = uiState.first().currentExpression
        val newCursorPosition = currentExpression.selection.start + 1

        val newExpression = expressionUtil.addParentheses(currentExpression)
        calculateExpression(
            currentExpression.copy(
                text = newExpression,
                selection = TextRange(newCursorPosition, newCursorPosition)
            )
        )
    }

    private fun updateTextFieldValue(
        value: TextFieldValue
    ) {
        val result = expressionUtil.calculateExpression(value.text)
        updateUiState(value, result)
    }

    private fun changeMoreActionsState(
        expanded: Boolean
    ) {
        _uiState.update { state ->
            state.copy(moreActionsExpanded = expanded)
        }
    }
}