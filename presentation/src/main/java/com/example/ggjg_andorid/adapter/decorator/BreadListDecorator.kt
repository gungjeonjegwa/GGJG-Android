package com.example.ggjg_andorid.adapter.decorator

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BreadListDecorator(val context: Context): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val deviceWidth = context.resources.displayMetrics.widthPixels
        if (deviceWidth <= 1080) {
            if (parent.getChildAdapterPosition(view) % 2 == 0) {
                outRect.right = dpToPx(4.5f)
            } else {
                outRect.left = dpToPx(4.5f)
            }
        } else {
            if (parent.getChildAdapterPosition(view) % 3 == 0) {
                outRect.right = dpToPx(4.5f)
            } else if (parent.getChildAdapterPosition(view) % 3 == 1) {
                outRect.right = dpToPx(4.5f)
                outRect.left = dpToPx(4.5f)
            } else {
                outRect.left = dpToPx(4.5f)
            }
        }
        outRect.bottom = dpToPx(48f)
    }

    private fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
    }
}