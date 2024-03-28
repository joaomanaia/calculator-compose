package com.infinitepower.calculator.compose.ui.home

import androidx.annotation.Keep
import androidx.compose.ui.text.input.TextFieldValue
import com.infinitepower.calculator.compose.core.AngleType
import com.infinitepower.calculator.compose.core.evaluator.Expressions

@Keep
data class HomeUiState(
    val currentExpression: TextFieldValue = TextFieldValue(text = ""),
    val moreActionsExpanded: Boolean = false,
    val angleType: AngleType = Expressions.DEFAULT_ANGLE_TYPE,
    val isInverse: Boolean = false
)
