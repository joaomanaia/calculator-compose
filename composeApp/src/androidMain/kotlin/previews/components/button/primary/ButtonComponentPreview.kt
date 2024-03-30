package previews.components.button.primary

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import core.ButtonAction
import core.presentation.theme.CalculatorTheme
import core.presentation.theme.spacing
import presentation.components.button.primary.ButtonComponent

@Composable
@PreviewLightDark
private fun SquareButtonComponentPreview() {
    CalculatorTheme {
        Surface {
            Column {
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
}

@Composable
@PreviewLightDark
private fun SmallButtonComponent2Preview() {
    CalculatorTheme {
        Surface {
            Column {
                ButtonComponent(
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(1f / 0.7f)
                        .padding(MaterialTheme.spacing.medium),
                    buttonAction = ButtonAction.Button1,
                    onClick = {}
                )
            }
        }
    }
}
