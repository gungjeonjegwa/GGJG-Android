package com.example.ggjg_andorid.utils.Extension

fun Int.toTotalMoney(extraMoney: Int?, count: Int): Int =
    (this + (extraMoney ?: 0)) * count