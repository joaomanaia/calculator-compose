package previews.components.expression

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import core.presentation.theme.CalculatorTheme
import core.presentation.theme.spacing
import model.AngleType
import presentation.components.expression.ExpressionContent

@Composable
@PreviewLightDark
private fun ExpressionContentPreview() {
    CalculatorTheme {
        Surface {
            ExpressionContent(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .fillMaxWidth()
                    .height(300.dp),
                currentExpression = TextFieldValue("21+3*"),
                result = "23",
                angleType = AngleType.DEG,
                updateTextFieldValue = {}
            )
        }
    }
}
