package com.example.ggjg_andorid.utils.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.widget.NestedScrollView

class CustomScrollView : NestedScrollView, ViewTreeObserver.OnGlobalLayoutListener {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        overScrollMode = OVER_SCROLL_NEVER
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    var header: View? = null
        set(value) {
            field = value
            field?.let {
                it.translationZ = 1f
            }
        }

    var item: View? = null

    var stickListener: (View) -> Unit = {}
    var freeListener: (View) -> Unit = {}
    var itemOverListener: (View) -> Unit = {}
    var itemUnOverListener: (View) -> Unit = {}
    var position = 0f

    private var mIsHeaderSticky = false

    private var mHeaderInitPosition = 0f
    private var mHeaderBottomPosition = 0f
    private var mItemInitPosition: Float? = null

    override fun onGlobalLayout() {
        mHeaderInitPosition = header?.top?.toFloat() ?: 0f
        mHeaderBottomPosition = header?.bottom?.toFloat() ?: 0f
        mItemInitPosition = item?.bottom?.toFloat()
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        position = t.toFloat()
        if (position > mHeaderInitPosition) {
            stickHeader(t - mHeaderInitPosition)
        } else {
            freeHeader()
        }
        if (mItemInitPosition != null && position > mItemInitPosition!! - mHeaderBottomPosition!!) {
            itemOverListener(header ?: return)
        } else {
            itemUnOverListener(header ?: return)
        }
    }

    private fun stickHeader(position: Float) {
        header?.translationY = position
        callStickListener()
    }

    private fun callStickListener() {
        if (!mIsHeaderSticky) {
            stickListener(header ?: return)
            mIsHeaderSticky = true
        }
    }

    private fun freeHeader() {
        header?.translationY = 0f
        callFreeListener()
    }

    private fun callFreeListener() {
        if (mIsHeaderSticky) {
            freeListener(header ?: return)
            mIsHeaderSticky = false
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

}