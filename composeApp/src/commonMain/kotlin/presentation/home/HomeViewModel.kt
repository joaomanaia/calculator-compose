package presentation.home

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.util.ExpressionUtil
import core.ButtonAction
import core.ButtonAction.*
import domain.result.ExpressionResult
import domain.result.ExpressionResultDataSource
import domain.time.DateTimeUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel (
    private val expressionUtil: ExpressionUtil,
//    private val expressionResultDataSource: ExpressionResultDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = combine(
        _uiState.asStateFlow(),
        emptyFlow<Any>()
//        expressionResultDataSource.getAllResultsFlow(),
    ) { uiState, results ->
//        uiState.copy(results = results)
        uiState
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HomeUiState()
    )

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
            is HomeUiEvent.InsertIntoExpression -> addValueToExpression(event.value)
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

    private fun addValueToExpression(value: String) {
        _uiState.update { currentState ->
            val currentExpression = currentState.currentExpression
            val newExpression = expressionUtil.addValueToExpression(value, currentExpression)

            currentState.copy(currentExpression = newExpression)
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
        _uiState.updateAndGet { currentState ->
            val result = expressionUtil.calculateExpression(currentState.currentExpression.text)

            // Save the result in the database
//            viewModelScope.launch {
//                expressionResultDataSource.insertResult(
//                    result = ExpressionResult(
//                        expression = currentState.currentExpression.text,
//                        result = result,
//                        createdAt = DateTimeUtil.now()
//                    )
//                )
//            }

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