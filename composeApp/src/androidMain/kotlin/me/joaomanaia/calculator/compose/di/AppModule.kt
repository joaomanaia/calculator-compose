package me.joaomanaia.calculator.compose.di

import me.joaomanaia.calculator.compose.core.util.ExpressionUtil
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.joaomanaia.calculator.compose.core.util.ExpressionUtilImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AppModule {
    @Binds
    abstract fun bindExpressionUtil(impl: ExpressionUtilImpl): ExpressionUtil
}
