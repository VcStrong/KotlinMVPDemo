package com.dingtao.common.util.recyclerview

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class SpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = spacing / 2
        outRect.bottom = spacing / 2
        outRect.left = spacing / 2
        outRect.right = spacing / 2
    }
}