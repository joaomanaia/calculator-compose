package presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.presentation.theme.spacing
import domain.result.ExpressionResult
import domain.time.DateTimeUtil

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HistoryList(
    modifier: Modifier = Modifier,
    results: List<ExpressionResult>,
    insertIntoExpression: (String) -> Unit
) {
    val spaceMedium = MaterialTheme.spacing.medium

    Surface(
        modifier = modifier,
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Surface(
                tonalElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = MaterialTheme.spacing.small,
                            horizontal = MaterialTheme.spacing.medium
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "History",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = "History more options",
                        )
                    }
                }
            }

            val resultsGrouped = remember(results) {
                results.groupBy { DateTimeUtil.formatDate(it.createdAt) }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    top = MaterialTheme.spacing.medium
                )
            ) {
                resultsGrouped.forEach { (date, results) ->
                    stickyHeader {
                        Text(
                            text = date,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(
                                start = spaceMedium,
                                end = spaceMedium,
                                bottom = spaceMedium
                            )
                        )
                    }

                    items(results) { result ->
                        Text(
                            text = result.expression,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = spaceMedium)
                                .clickable { insertIntoExpression(result.expression) }
                                .padding(
                                    vertical = MaterialTheme.spacing.tiny,
                                    horizontal = spaceMedium
                                )
                        )
                        Text(
                            text = result.result,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { insertIntoExpression(result.result) }
                                .padding(
                                    vertical = MaterialTheme.spacing.tiny,
                                    horizontal = spaceMedium
                                )
                        )
                    }
                }
            }
        }
    }
}
