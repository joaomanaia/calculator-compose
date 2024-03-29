package com.infinitepower.calculator.compose.ui.components.button.secondary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import me.joaomanaia.calculator.compose.ui.components.button.ButtonAction
import me.joaomanaia.calculator.compose.ui.components.button.ButtonAction.Companion.getColorByButton
import me.joaomanaia.calculator.compose.ui.theme.CalculatorTheme
import me.joaomanaia.calculator.compose.ui.theme.spacing

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

@Composable
@PreviewLightDark
private fun SecondaryButtonComponentPreview() {
    CalculatorTheme {
        Surface {
            SecondaryButtonComponent(
                modifier = Modifier
                    .size(100.dp)
                    .padding(MaterialTheme.spacing.medium),
                buttonAction = ButtonAction.ButtonPI,
                onClick = {}
            )
        }
    }
}
