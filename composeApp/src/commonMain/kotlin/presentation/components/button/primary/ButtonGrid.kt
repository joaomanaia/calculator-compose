package presentation.components.button.primary

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.ButtonAction
import core.presentation.theme.CalculatorTheme
import core.presentation.theme.spacing

@Composable
internal fun ButtonGrid(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    onActionClick: (action: ButtonAction) -> Unit
) {
    val actions = ButtonAction.getAllPrimaryButtons(isPortrait = isPortrait)

    ButtonGridImpl(
        modifier = modifier,
        isPortrait = isPortrait,
        actions = actions,
        onActionClick = onActionClick
    )
}

@Composable
private fun ButtonGridImpl(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    actions: List<ButtonAction>,
    onActionClick: (action: ButtonAction) -> Unit
) {
    val spaceSmall = MaterialTheme.spacing.small

    val actionsChunked = remember(actions, isPortrait) {
        val itemsPerCol = if (isPortrait) 4 else 5

        actions.chunked(itemsPerCol)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spaceSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        actionsChunked.forEach { rowActions ->
            Row(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement = Arrangement.spacedBy(spaceSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowActions.forEach { action ->
                    ButtonComponent(
                        modifier = Modifier.weight(1f),
                        buttonAction = action,
                        onClick = { onActionClick(action) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ButtonGridPreview() {
    CalculatorTheme {
        Surface {
            ButtonGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                isPortrait = true,
                onActionClick = {}
            )
        }
    }
}