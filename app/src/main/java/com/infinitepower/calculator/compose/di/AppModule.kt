package com.infinitepower.calculator.compose.di

import com.infinitepower.calculator.compose.core.util.ExpressionUtil
import com.infinitepower.calculator.compose.core.util.ExpressionUtilImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AppModule {
    @Binds
    abstract fun bindExpressionUtil(impl: ExpressionUtilImpl): ExpressionUtil
}