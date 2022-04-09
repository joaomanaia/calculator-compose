package com.infinitepower.calculator.compose.ui.home

import androidx.annotation.Keep
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

@Keep
data class HomeUiState(
    val currentExpression: TextFieldValue = TextFieldValue(text = ""),
    val result: String = "",
)