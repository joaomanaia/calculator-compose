package domain.result

import kotlinx.coroutines.flow.Flow

interface ExpressionResultDataSource {
    suspend fun insertResult(result: ExpressionResult)

    fun getAllResultsFlow(): Flow<List<ExpressionResult>>

    suspend fun deleteResult(id: Long)
}
