package presentation.components.button.primary

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.ButtonAction
import core.ButtonAction.Companion.getColorByButton
import core.presentation.theme.CalculatorTheme
import core.presentation.theme.spacing
import presentation.components.text.AutoSizeText

@Composable
internal fun ButtonComponent(
    modifier: Modifier = Modifier,
    buttonAction: ButtonAction,
    onClick: () -> Unit
) {
    val color = buttonAction.getColorByButton()

    ButtonComponent(
        modifier = modifier,
        text = buttonAction.value,
        surfaceColor = color,
        onClick = onClick
    )
}

@Composable
private fun ButtonComponent(
    modifier: Modifier = Modifier,
    text: String,
    surfaceColor: Color = MaterialTheme.colorScheme.surface,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val surfacePressed by interactionSource.collectIsPressedAsState()
    val surfaceShape by animateDpAsState(
        targetValue = if (surfacePressed) SURFACE_PRESS_SHAPE else SURFACE_CIRCLE_SHAPE,
        animationSpec = tween(durationMillis = 250, easing = LinearEasing),
        label = "Surface Shape"
    )

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(surfaceShape),
        tonalElevation = 1.dp,
        color = surfaceColor,
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AutoSizeText(
                text = text,
                modifier = Modifier.padding(8.dp),
                color = contentColorFor(backgroundColor = surfaceColor)
            )
        }
    }
}

private val SURFACE_CIRCLE_SHAPE = 100.dp
private val SURFACE_PRESS_SHAPE = 20.dp