package presentation.components.expression

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.ParagraphIntrinsics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.util.ExpressionUtilImpl
import core.presentation.theme.spacing
import presentation.util.text.createFontFamilyResolver
import model.AngleType

@Composable
fun ExpressionContent(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    currentExpression: TextFieldValue,
    result: String,
    angleType: AngleType,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    updateTextFieldValue: (value: TextFieldValue) -> Unit
) {
    val spaceMedium = MaterialTheme.spacing.medium

    // Show angle text if the expression contains angle functions
    val showAngleText = remember(currentExpression.text) {
        derivedStateOf {
            ExpressionUtilImpl.angleFunctions.any { currentExpression.text.contains(it) }
        }
    }

    Surface(
        tonalElevation = 8.dp,
        modifier = modifier,
        shape = shape
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(spaceMedium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (showAngleText.value) {
                    Text(
                        text = angleType.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "More options",
                    )
                }
            }

            CompositionLocalProvider(
                // Prevents opening the keyboard when the text field is tapped
                value = LocalTextInputService provides null
            ) {
                AutoSizeTextField(
                    inputValue = currentExpression,
                    inputValueChanged = updateTextFieldValue,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            SelectionContainer {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    text = result,
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}

private const val TEXT_SCALE_REDUCTION_INTERVAL = 0.9f

// Original: https://github.com/banmarkovic/AutoSizeTextField
@Composable
@OptIn(ExperimentalTextApi::class)
private fun AutoSizeTextField(
    modifier: Modifier = Modifier,
    inputValue: TextFieldValue,
    maxFontSize: TextUnit = 100.sp,
    minFontSize: TextUnit = 30.sp,
    inputValueChanged: (TextFieldValue) -> Unit,
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        var shrunkFontSize = maxFontSize

        val calculateIntrinsics = @Composable {
            ParagraphIntrinsics(
                text = inputValue.text,
                style = TextStyle(
                    fontSize = shrunkFontSize,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ),
                density = LocalDensity.current,
                fontFamilyResolver = createFontFamilyResolver()
            )
        }

        var intrinsics = calculateIntrinsics()
        with(LocalDensity.current) {
            // TextField and OutlinedText field have default horizontal padding of 16.dp
            val textFieldDefaultHorizontalPadding = 16.dp.toPx()
            val maxInputWidth = maxWidth.toPx() - 2 * textFieldDefaultHorizontalPadding

            while (intrinsics.maxIntrinsicWidth > maxInputWidth && shrunkFontSize > minFontSize) {
                shrunkFontSize *= TEXT_SCALE_REDUCTION_INTERVAL
                if (shrunkFontSize < minFontSize) {
                    shrunkFontSize = minFontSize
                    break
                }
                intrinsics = calculateIntrinsics()
            }
        }

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    state = rememberScrollState(),
                    reverseScrolling = true,
                ),
            value = inputValue,
            onValueChange = { inputValueChanged(it) },
            textStyle = TextStyle(
                fontSize = shrunkFontSize,
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.onSurface
            ),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            readOnly = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
            ),
        )
    }
}
