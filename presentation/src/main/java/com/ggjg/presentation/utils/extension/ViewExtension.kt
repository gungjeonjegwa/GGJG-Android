package com.ggjg.presentation.utils.extension

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import com.ggjg.presentation.R

fun View.setVisible(visible: Boolean = true) {
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.heartChange(activated: Boolean, context: Context) {
    isActivated = activated
    if (isActivated) {
        this.startAnimation(AnimationUtils.loadAnimation(context, R.anim.heart_big_anim))
    }
}