package com.ggjg.presentation.utils.extension

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.widget.NestedScrollView
import kotlin.math.abs

fun NestedScrollView.customTopScroll(clickView: View) {
    if (this.getChildAt(0).height <= this.height) return
    val ratio = abs(0 - this.scrollY) / (this.getChildAt(0).height - this.height).toFloat()
    val scrollAnimation = ObjectAnimator.ofInt(this, "scrollY", 0).apply {
        duration = (1000L * ratio).toLong()
    }
    clickView.setOnTouchListener { _, _ ->
        scrollAnimation.pause()
        false
    }
    scrollAnimation.start()
}