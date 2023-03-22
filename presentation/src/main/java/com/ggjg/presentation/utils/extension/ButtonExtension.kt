package com.ggjg.presentation.utils.extension

import android.widget.Button

fun Button.changeActivatedWithEnabled(activated: Boolean) {
    isActivated = activated
    isEnabled = isActivated
}