package com.infinitepower.calculator.compose.core

enum class AngleType {
    DEG, RAD;

    fun next(): AngleType {
        return when (this) {
            DEG -> RAD
            RAD -> DEG
        }
    }
}
