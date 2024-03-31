package previews.components.button.secondary

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import core.presentation.theme.CalculatorTheme
import model.AngleType
import presentation.components.button.secondary.MoreSecondaryActionsItem
import presentation.components.button.secondary.SecondaryButtonGrid

@Composable
@Preview(
    showBackground = true,
    group = "Button Grid"
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    group = "Button Grid"
)
private fun ButtonGridPreview() {
    var buttonGridExpanded by remember { mutableStateOf(true) }

    CalculatorTheme {
        Surface {
            SecondaryButtonGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                isPortrait = true,
                buttonGridExpanded = buttonGridExpanded,
                angleType = AngleType.DEG,
                isInverse = false,
                onActionClick = {},
                onMoreActionsClick = { buttonGridExpanded = !buttonGridExpanded }
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun MoreSecondaryActionsItemPreview() {
    var buttonGridExpanded by remember { mutableStateOf(false) }

    CalculatorTheme {
        Surface {
            MoreSecondaryActionsItem(
                onClick = { buttonGridExpanded = !buttonGridExpanded },
                buttonGridExpanded = buttonGridExpanded,
                verticalContent = true
            )
        }
    }
}
