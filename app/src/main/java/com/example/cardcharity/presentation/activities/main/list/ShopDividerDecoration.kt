package com.example.cardcharity.presentation.activities.main.list

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cardcharity.R
import com.example.cardcharity.utils.extensions.attr
import com.example.cardcharity.utils.extensions.dpToPx
import com.example.cardcharity.utils.extensions.setColor
import com.example.cardcharity.utils.extensions.transparent


class ShopDividerDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    private val divider: Drawable

    init {
        val styledAttributes = context.obtainStyledAttributes(DIVIDER_ATTR)
        divider = checkNotNull(styledAttributes.getDrawable(0)).apply {
            setColor(context.attr(R.attr.colorOnSurface).transparent(12))
        }
        styledAttributes.recycle()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (i: Int in 0..parent.childCount - 2) {
            val child = parent.getChildAt(i)
            val position = parent.getPosition(child) ?: return
            val type = parent.getItemType(position)
            val nextType = parent.getItemType(position + 1)

            if (type == MODEL_ORDINAL && nextType == MODEL_ORDINAL) {
                val params = child.layoutParams as RecyclerView.LayoutParams
                val left = parent.paddingLeft + dpToPx(context, 116)
                val right = parent.width - parent.paddingRight
                val top = child.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight

                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }
    }

    private fun RecyclerView.getPosition(child: View): Int? {
        return getChildAdapterPosition(child).let {
            if (it == RecyclerView.NO_POSITION) return null else it
        }
    }

    private fun RecyclerView.getItemType(position: Int?): Int? {
        return adapter?.getItemViewType(position ?: return null)
    }

    companion object {
        private val MODEL_ORDINAL = ModelType.MODEL.ordinal
        private val DIVIDER_ATTR = intArrayOf(android.R.attr.listDivider)
    }
}