package com.teknokrait.mussichusettsapp.view.custom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Aprilian Nur Wakhid Daini on 7/19/2019.
 */
class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getAdapter() != null && parent.getChildAdapterPosition(view) !== parent.getAdapter()!!.getItemCount() - 1) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}