package com.example.ggjg_andorid.utils

import android.app.Activity
import android.widget.EditText
import android.widget.TextView

fun TextView.onError(error: String, editText: EditText, context: Activity) {
    this.text = error
    keyboardShow(context, editText)
}