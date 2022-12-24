package com.example.ggjg_andorid.utils

import android.widget.Button

fun Button.changeActivatedWithEnabled(activated: Boolean) {
    isActivated = activated
    isEnabled = isActivated
}