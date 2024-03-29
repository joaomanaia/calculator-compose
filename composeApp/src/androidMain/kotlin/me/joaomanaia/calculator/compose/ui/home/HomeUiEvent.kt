package me.joaomanaia.calculator.compose.ui.home

import androidx.compose.ui.text.input.TextFieldValue
import me.joaomanaia.calculator.compose.ui.components.button.ButtonAction

sealed interface HomeUiEvent {
    data class OnButtonActionClick(
        val action: ButtonAction
    ) : HomeUiEvent

    data class UpdateTextFieldValue(
        val value: TextFieldValue
    ) : HomeUiEvent

    data object OnChangeMoreActionsClick : HomeUiEvent
}
