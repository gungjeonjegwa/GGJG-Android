package com.ggjg.presentation.utils.extension

fun Int.toTotalMoney(extraMoney: Int?, count: Int): Int =
    (this + (extraMoney ?: 0)) * count