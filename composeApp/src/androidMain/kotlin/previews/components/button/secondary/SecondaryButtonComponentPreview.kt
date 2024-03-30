package previews.components.button.secondary

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import core.ButtonAction
import core.presentation.theme.CalculatorTheme
import core.presentation.theme.spacing
import presentation.components.button.secondary.SecondaryButtonComponent

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
