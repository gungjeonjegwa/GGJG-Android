package com.ggjg.presentation.utils.extension

import android.content.res.Resources

fun Int.toTotalMoney(extraMoney: Int?, count: Int): Int =
    (this + (extraMoney ?: 0)) * count

fun Int.toDp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()