package com.infinitepower.calculator.compose.ui.components.button.primary

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction
import com.infinitepower.calculator.compose.ui.theme.CalculatorTheme
import com.infinitepower.calculator.compose.ui.theme.spacing

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
    val spaceMedium = MaterialTheme.spacing.medium

    val itemsPerCol = remember(isPortrait) {
        if (isPortrait) 5 else 4
    }

    val gridPadding = if (isPortrait) {
        PaddingValues(
            start = spaceMedium,
            end = spaceMedium,
            bottom = spaceMedium
        )
    } else PaddingValues(0.dp)

    BoxWithConstraints(
        modifier = modifier
    ) {
        val heightPerItem = remember(maxHeight, itemsPerCol) {
            maxHeight / itemsPerCol - 8.dp
        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(count = if (isPortrait) 4 else 5),
            verticalArrangement = Arrangement.spacedBy(spaceSmall),
            horizontalArrangement = Arrangement.spacedBy(spaceSmall),
            contentPadding = gridPadding,
            userScrollEnabled = false
        ) {
            items(items = actions) { action ->
                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Ensure that the item height is not greater than the max width,
                    // to make sure that the item is square and not too tall.
                    val itemHeight = minOf(maxWidth, heightPerItem)

                    ButtonComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(itemHeight),
                        buttonAction = action,
                        onClick = { onActionClick(action) }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    group = "Portrait"
)
@Preview(
    showBackground = true,
    group = "Landscape",
    device = "spec:shape=Normal,width=2340,height=1080,unit=px,dpi=440"
)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun ButtonGridPreview() {
    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT

    CalculatorTheme {
        Surface {
            ButtonGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                isPortrait = isPortrait,
                onActionClick = {}
            )
        }
    }
}