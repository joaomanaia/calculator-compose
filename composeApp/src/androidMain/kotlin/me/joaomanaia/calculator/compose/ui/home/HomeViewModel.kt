package me.joaomanaia.calculator.compose.ui.home

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.joaomanaia.calculator.compose.core.util.ExpressionUtil
import me.joaomanaia.calculator.compose.ui.components.button.ButtonAction
import me.joaomanaia.calculator.compose.ui.components.button.ButtonAction.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val expressionUtil: ExpressionUtil
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    val result = _uiState
        .distinctUntilChanged { old, new ->
            old.currentExpression.text == new.currentExpression.text && old.angleType == new.angleType
        }.map { state ->
            expressionUtil.calculateExpression(state.currentExpression.text)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ""
        )

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
            else -> addActionValueToExpression(action)
        }
    }

    private fun changeAngleType() {
        _uiState.update { state ->
            val newAngleType = state.angleType.next()
            expressionUtil.changeAngleMode(newAngleType)

            state.copy(angleType = newAngleType)
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

            currentState.copy(currentExpression = newExpression)
        }
    }

    private fun clearExpression() {
        _uiState.update { currentState ->
            currentState.copy(currentExpression = TextFieldValue())
        }
    }

    /**
     * Replace the current expression with the result and set the cursor at the end of the expression
     */
    private fun replaceResultInExpression() {
        _uiState.update { currentState ->
            val result = expressionUtil.calculateExpression(currentState.currentExpression.text)

            currentState.copy(
                currentExpression = TextFieldValue(
                    text = result,
                    // Set the cursor at the end of the expression
                    selection = TextRange(result.length)
                )
            )
        }
    }

    private fun removeDigit() {
        _uiState.update { currentState ->
            val currentExpression = currentState.currentExpression
            val newExpression = expressionUtil.removeDigit(currentExpression)

            currentState.copy(currentExpression = newExpression)
        }
    }

    private fun addParentheses() {
        _uiState.update { uiState ->
            val currentExpression = uiState.currentExpression
            val newExpression = expressionUtil.addParentheses(currentExpression)

            uiState.copy(currentExpression = newExpression)
        }
    }

    private fun updateTextFieldValue(value: TextFieldValue) {
        _uiState.update { state ->
            state.copy(currentExpression = value)
        }
    }

    private fun changeMoreActionsState() {
        _uiState.update { state ->
            state.copy(moreActionsExpanded = !state.moreActionsExpanded)
        }
    }
}