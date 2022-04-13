package com.infinitepower.calculator.compose.ui.components.expression_content

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinitepower.calculator.compose.ui.home.HomeUiEvent
import com.infinitepower.calculator.compose.ui.theme.CalculatorTheme
import com.infinitepower.calculator.compose.ui.theme.spacing

@Composable
fun ExpressionContent(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    currentExpression: TextFieldValue,
    result: String,
    updateTextFieldValue: (value: TextFieldValue) -> Unit
) {
    val spaceMedium = MaterialTheme.spacing.medium
    val spaceLarge = MaterialTheme.spacing.large

    val scrollState = rememberScrollState()

    Surface(
        tonalElevation = 8.dp,
        modifier = modifier,
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(spaceMedium)
        ) {
            Text(
                text = "Current expression",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    MaterialTheme.spacing.extraSmall
                ),
                modifier = Modifier.padding(
                    top = if (isPortrait) spaceLarge else 0.dp
                )
            ) {
                BasicTextField(
                    value = currentExpression,
                    onValueChange = updateTextFieldValue,
                    textStyle = MaterialTheme.typography.displayLarge.copy(
                        letterSpacing = 8.sp,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.inverseSurface
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(
                            state = scrollState,
                            reverseScrolling = true,
                        ),
                    maxLines = 1,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    readOnly = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    )
                )
            }
            if (isPortrait) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                SelectionContainer {
                    Text(
                        text = result,
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun ExpressionContentPreview() {
    CalculatorTheme {
        Surface {
            ExpressionContent(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                isPortrait = false,
                currentExpression = TextFieldValue("1 + 2"),
                result = "3",
                updateTextFieldValue = {}
            )
        }
    }
}