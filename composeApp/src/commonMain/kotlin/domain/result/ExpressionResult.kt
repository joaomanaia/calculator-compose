package domain.result

import kotlinx.datetime.LocalDateTime

data class ExpressionResult(
    val expression: String,
    val result: String,
    val createdAt: LocalDateTime
)
