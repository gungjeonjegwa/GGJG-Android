package com.example.ggjg_andorid.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun keyboardHide(context: Activity, view: EditText) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        context.currentFocus?.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
    view.clearFocus()
}

fun keyboardShow(context: Activity) {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(
        InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}