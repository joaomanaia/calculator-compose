package com.infinitepower.calculator.compose.ui.components.button.primary

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    buttonGridExpanded: Boolean = true,
    onActionClick: (action: ButtonAction) -> Unit
) {
    val actions = ButtonAction.getAllPrimaryButtons(isPortrait = isPortrait)

    ButtonGridImpl(
        modifier = modifier,
        isPortrait = isPortrait,
        buttonGridExpanded = buttonGridExpanded,
        actions = actions,
        onActionClick = onActionClick
    )
}

@Composable
private fun ButtonGridImpl(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    buttonGridExpanded: Boolean,
    actions: List<ButtonAction>,
    onActionClick: (action: ButtonAction) -> Unit
) {
    val spaceSmall = MaterialTheme.spacing.small
    val spaceMedium = MaterialTheme.spacing.medium

    val buttonAspectRatio by animateFloatAsState(
        targetValue = when {
            isPortrait && !buttonGridExpanded -> 1f
            isPortrait && buttonGridExpanded -> 1f / 0.7f
            else -> 1f / 0.5f
        }
    )

    val gridPadding = if (isPortrait) {
        PaddingValues(
            start = spaceMedium,
            end = spaceMedium,
            bottom = spaceMedium
        )
    } else PaddingValues(0.dp)

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = if (isPortrait) 4 else 5),
        verticalArrangement = Arrangement.spacedBy(spaceSmall),
        horizontalArrangement = Arrangement.spacedBy(spaceSmall),
        contentPadding = gridPadding,
        userScrollEnabled = false
    ) {
        items(items = actions) { action ->
            ButtonComponent(
                buttonAction = action,
                isPortrait = isPortrait,
                buttonGridExpanded = buttonGridExpanded,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(buttonAspectRatio),
                onClick = { onActionClick(action) }
            )
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
                modifier = Modifier.fillMaxSize(),
                isPortrait = isPortrait,
                buttonGridExpanded = true,
                onActionClick = {}
            )
        }
    }
}