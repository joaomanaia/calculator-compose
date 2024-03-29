package me.joaomanaia.calculator.compose.ui.home

import androidx.annotation.Keep
import androidx.compose.ui.text.input.TextFieldValue
import me.joaomanaia.calculator.compose.core.AngleType
import me.joaomanaia.calculator.compose.core.evaluator.Expressions

@Keep
data class HomeUiState(
    val currentExpression: TextFieldValue = TextFieldValue(text = ""),
    val moreActionsExpanded: Boolean = false,
    val angleType: AngleType = Expressions.DEFAULT_ANGLE_TYPE,
    val isInverse: Boolean = false
)
