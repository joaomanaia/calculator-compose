package com.infinitepower.calculator.compose.ui.components.button.primary

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.infinitepower.calculator.compose.ui.components.button.ButtonAction
import com.infinitepower.calculator.compose.ui.theme.CalculatorTheme
import com.infinitepower.calculator.compose.ui.theme.spacing

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun ButtonGrid(
    modifier: Modifier = Modifier,
    buttonGridExpanded: Boolean = true,
    onActionClick: (action: ButtonAction) -> Unit
) {
    val actions = ButtonAction.getAllPrimaryButtons()

    ButtonGridImpl(
        modifier = modifier,
        buttonGridExpanded = buttonGridExpanded,
        actions = actions,
        onActionClick = onActionClick
    )
}

@Composable
@ExperimentalFoundationApi
private fun ButtonGridImpl(
    modifier: Modifier = Modifier,
    buttonGridExpanded: Boolean,
    actions: List<ButtonAction>,
    onActionClick: (action: ButtonAction) -> Unit
) {
    val spaceSmall = MaterialTheme.spacing.small
    val spaceMedium = MaterialTheme.spacing.medium

    val buttonAspectRatio by animateFloatAsState(
        targetValue = if (buttonGridExpanded) 1f else 1f / 0.7f
    )

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(count = 4),
        verticalArrangement = Arrangement.spacedBy(spaceSmall),
        horizontalArrangement = Arrangement.spacedBy(spaceSmall),
        contentPadding = PaddingValues(
            start = spaceMedium,
            end = spaceMedium,
            bottom = spaceMedium
        )
    ) {
        items(items = actions) { action ->
            ButtonComponent(
                buttonAction = action,
                buttonGridExpanded = buttonGridExpanded,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(buttonAspectRatio),
                onClick = { onActionClick(action) }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
private fun ButtonGridPreview() {
    CalculatorTheme {
        Surface {
            ButtonGrid(
                modifier = Modifier.fillMaxSize(),
                buttonGridExpanded = true,
                onActionClick = {}
            )
        }
    }
}