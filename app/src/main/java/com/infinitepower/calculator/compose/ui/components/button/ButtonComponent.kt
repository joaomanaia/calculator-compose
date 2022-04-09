package com.infinitepower.calculator.compose.ui.components.button

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction.Companion.getColorByButton
import com.infinitepower.calculator.compose.ui.theme.CalculatorTheme
import com.infinitepower.calculator.compose.ui.theme.spacing

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ButtonComponent(
    modifier: Modifier = Modifier,
    buttonAction: ButtonAction,
    onClick: () -> Unit
) {
    val color = buttonAction.getColorByButton()

    ButtonComponentImpl(
        modifier = modifier,
        actionText = buttonAction.value,
        color = color,
        onClick = onClick
    )
}

@Composable
@ExperimentalMaterial3Api
private fun ButtonComponentImpl(
    modifier: Modifier = Modifier,
    actionText: String,
    color: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val surfacePressed by interactionSource.collectIsPressedAsState()
    val surfaceShape by animateDpAsState(
        targetValue = if (surfacePressed) 20.dp else 100.dp,
        animationSpec = tween(durationMillis = 250, easing = LinearEasing)
    )

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(surfaceShape),
        tonalElevation = 1.dp,
        color = color,
        onClick = onClick,
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun ButtonComponentPreview() {
    CalculatorTheme {
        Surface {
            ButtonComponent(
                modifier = Modifier
                    .size(100.dp)
                    .padding(MaterialTheme.spacing.medium),
                buttonAction = ButtonAction.Button1,
                onClick = {}
            )
        }
    }
}