package com.infinitepower.calculator.compose.di

import com.infinitepower.calculator.compose.core.evaluator.Expressions
import com.infinitepower.calculator.compose.core.util.ExpressionUtil
import com.infinitepower.calculator.compose.core.util.ExpressionUtilImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideExpressions(): Expressions = Expressions()

    @Provides
    @Singleton
    fun provideExpressionUtil(
        expressions: Expressions
    ): ExpressionUtil = ExpressionUtilImpl(expressions)
}