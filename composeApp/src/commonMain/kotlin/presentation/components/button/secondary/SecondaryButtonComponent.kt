package presentation.components.button.secondary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import core.ButtonAction
import core.ButtonAction.Companion.getColorByButton

@Composable
fun SecondaryButtonComponent(
    modifier: Modifier = Modifier,
    buttonAction: ButtonAction,
    onClick: () -> Unit
) {
    val color = buttonAction.getColorByButton()

    SecondaryButtonComponentImpl(
        modifier = modifier,
        actionText = buttonAction.displayText,
        color = color,
        onClick = onClick
    )
}

@Composable
private fun SecondaryButtonComponentImpl(
    modifier: Modifier = Modifier,
    actionText: String,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.aspectRatio(BUTTON_RATIO),
        shape = CircleShape,
        color = color,
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

private const val BUTTON_RATIO = 1f / 0.8f