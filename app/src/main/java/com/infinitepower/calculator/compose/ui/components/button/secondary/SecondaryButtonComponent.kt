package com.infinitepower.calculator.compose.ui.components.button.secondary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction.Companion.getColorByButton
import com.infinitepower.calculator.compose.ui.theme.CalculatorTheme
import com.infinitepower.calculator.compose.ui.theme.spacing

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SecondaryButtonComponent(
    modifier: Modifier = Modifier,
    buttonAction: ButtonAction,
    onClick: () -> Unit
) {
    val color = buttonAction.getColorByButton()

    SecondaryButtonComponentImpl(
        modifier = modifier,
        actionText = buttonAction.value,
        color = color,
        onClick = onClick
    )
}

@Composable
@ExperimentalMaterial3Api
private fun SecondaryButtonComponentImpl(
    modifier: Modifier = Modifier,
    actionText: String,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.aspectRatio(1f / 0.8f),
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

@Composable
@Preview(showBackground = true)
private fun SecondaryButtonComponentPreview() {
    CalculatorTheme {
        Surface {
            SecondaryButtonComponent(
                modifier = Modifier
                    .size(100.dp)
                    .padding(MaterialTheme.spacing.medium),
                buttonAction = ButtonAction.Button1,
                onClick = {}
            )
        }
    }
}