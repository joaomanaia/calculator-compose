package presentation.home

import androidx.compose.ui.text.input.TextFieldValue
import model.AngleType
import core.evaluator.Expressions

data class HomeUiState(
    val currentExpression: TextFieldValue = TextFieldValue(text = ""),
    val moreActionsExpanded: Boolean = false,
    val angleType: AngleType = Expressions.DEFAULT_ANGLE_TYPE,
    val isInverse: Boolean = false
)
