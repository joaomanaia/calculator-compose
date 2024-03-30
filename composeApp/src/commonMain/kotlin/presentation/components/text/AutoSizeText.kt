package presentation.components.text

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.presentation.theme.CalculatorTheme

@Composable
internal fun AutoSizeText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = LocalContentColor.current
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val maxSize = minOf(maxWidth, maxHeight)

        val fontSize = with(LocalDensity.current) {
            maxSize.toSp() * 0.5f
        }

        val style = LocalTextStyle.current.copy(
            fontSize = fontSize,
            textAlign = TextAlign.Center,
        )

        Text(
            text = text,
            color = color,
            style = style,
            softWrap = false,
            maxLines = 1,
            modifier = Modifier
        )
    }
}

@Composable
private fun AutoSizeTextPreview() {
    CalculatorTheme {
        Surface {
            AutoSizeText(
                modifier = Modifier.width(150.dp).aspectRatio(1f),
                text = "+"
            )
        }
    }
}
