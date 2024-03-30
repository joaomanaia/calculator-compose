package presentation.home

import androidx.compose.ui.text.input.TextFieldValue
import core.ButtonAction

sealed interface HomeUiEvent {
    data class OnButtonActionClick(
        val action: ButtonAction
    ) : HomeUiEvent

    data class UpdateTextFieldValue(
        val value: TextFieldValue
    ) : HomeUiEvent

    data object OnChangeMoreActionsClick : HomeUiEvent
}
