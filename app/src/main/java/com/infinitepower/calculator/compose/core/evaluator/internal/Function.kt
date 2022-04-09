package com.infinitepower.calculator.compose.core.evaluator.internal

import java.math.BigDecimal

abstract class Function {
    abstract fun call(arguments: List<BigDecimal>): BigDecimal
}