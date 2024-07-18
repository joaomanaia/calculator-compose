package di

import core.evaluator.internal.Evaluator
import core.evaluator.Expressions
import core.util.ExpressionUtil
import core.util.ExpressionUtilImpl
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import presentation.home.HomeViewModel

val evaluatorModule = module {
    singleOf(::Evaluator) {
        createdAtStart()
    }

    singleOf(::Expressions) {
        createdAtStart()
    }

    singleOf(::ExpressionUtilImpl) {
        bind<ExpressionUtil>()
        createdAtStart()
    }

    viewModelOf(::HomeViewModel)
}
