package com.infinitepower.calculator.compose.ui.components.button

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.infinitepower.calculator.compose.ui.theme.CalculatorTheme
import com.infinitepower.calculator.compose.ui.theme.spacing

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun ButtonGrid(
    modifier: Modifier = Modifier,
    onActionClick: (action: ButtonAction) -> Unit
) {
    val actions = ButtonAction.getAllButtons()

    ButtonGridImpl(
        modifier = modifier,
        actions = actions,
        onActionClick = onActionClick
    )
}

@Composable
@ExperimentalFoundationApi
fun ButtonGridImpl(
    modifier: Modifier = Modifier,
    actions: List<ButtonAction>,
    onActionClick: (action: ButtonAction) -> Unit
) {
    val spaceSmall = MaterialTheme.spacing.small

    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(count = 4),
        verticalArrangement = Arrangement.spacedBy(spaceSmall),
        horizontalArrangement = Arrangement.spacedBy(spaceSmall),
        contentPadding = PaddingValues(MaterialTheme.spacing.medium)
    ) {
        items(items = actions) { action ->
            ButtonComponent(
                buttonAction = action,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(1f),
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
                onActionClick = {}
            )
        }
    }
}