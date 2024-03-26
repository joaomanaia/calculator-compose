package com.infinitepower.calculator.compose.ui.home

import androidx.annotation.Keep
import androidx.compose.ui.text.input.TextFieldValue
import com.infinitepower.calculator.compose.core.AngleType

@Keep
data class HomeUiState(
    val currentExpression: TextFieldValue = TextFieldValue(text = ""),
    val result: String = "",
    val moreActionsExpanded: Boolean = false,
    val angleType: AngleType = AngleType.DEG,
    val isInverse: Boolean = false
)