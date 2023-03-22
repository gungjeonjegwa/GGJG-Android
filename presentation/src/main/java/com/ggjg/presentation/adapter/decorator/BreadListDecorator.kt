package com.ggjg.presentation.adapter.decorator

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.utils.Extension.toPx

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
                outRect.right = 4.5f.toPx(context)
            } else {
                outRect.left = 4.5f.toPx(context)
            }
        } else {
            if (parent.getChildAdapterPosition(view) % 3 == 0) {
                outRect.right = 4.5f.toPx(context)
            } else if (parent.getChildAdapterPosition(view) % 3 == 1) {
                outRect.right = 4.5f.toPx(context)
                outRect.left = 4.5f.toPx(context)
            } else {
                outRect.left = 4.5f.toPx(context)
            }
        }
        outRect.bottom = 48f.toPx(context)
    }
}