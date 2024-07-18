package presentation.home

import androidx.compose.ui.text.input.TextFieldValue
import model.AngleType
import core.evaluator.Expressions
import domain.result.ExpressionResult

data class HomeUiState(
    val currentExpression: TextFieldValue = TextFieldValue(text = ""),
    val moreActionsExpanded: Boolean = false,
    val angleType: AngleType = Expressions.DEFAULT_ANGLE_TYPE,
    val isInverse: Boolean = false,
    val results: List<ExpressionResult> = emptyList(),
)
