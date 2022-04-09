package com.infinitepower.calculator.compose.ui.home

import androidx.compose.ui.text.input.TextFieldValue
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction

sealed class HomeUiEvent {
    data class OnButtonActionClick(
        val action: ButtonAction
    ) : HomeUiEvent()

    data class UpdateTextFieldValue(
        val value: TextFieldValue
    ) : HomeUiEvent()
}
