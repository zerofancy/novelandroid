package top.ntutn.commonui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpacesItemDecoration(
    private val space: Int,
    @RecyclerView.Orientation private val orientation: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val offset1 = if (position == 0) space else space / 2
        val offset2 = if (position == parent.childCount - 1) space else space / 2
        if (orientation == RecyclerView.HORIZONTAL) {
            outRect.left = offset1
            outRect.right = offset2
        } else {
            outRect.top = offset1
            outRect.bottom = offset2
        }
    }
}