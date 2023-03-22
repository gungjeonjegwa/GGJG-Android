package com.ggjg.presentation.utils.extension

import android.app.Activity
import android.widget.EditText
import android.widget.TextView
import com.ggjg.presentation.utils.keyboardShow

fun TextView.onError(error: String, editText: EditText, context: Activity) {
    this.text = error
    keyboardShow(context, editText)
}