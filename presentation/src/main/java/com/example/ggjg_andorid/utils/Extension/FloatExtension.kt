package com.example.ggjg_andorid.utils.Extension

import android.content.Context
import android.util.TypedValue

fun Float.toPx(context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    ).toInt()
