package com.example.cardcharity.presentation.component.recyclerview.sticky

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StickyHeaderDecoration<T, VH : RecyclerView.ViewHolder>(
    rv: RecyclerView,
    listener: StickyHeaderInterface<T, VH>
) : RecyclerView.ItemDecoration() {
    private val mListener: StickyHeaderInterface<T, VH> = listener
    private var mStickyHeaderHeight = 0

    init {
        rv.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return e.y <= mStickyHeaderHeight
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val topChild = parent.getChildAt(0) ?: return

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val currentHeader: View = getHeaderViewForItem(topChildPosition, parent).itemView
        fixLayoutSize(parent, currentHeader)
        val contactPoint = currentHeader.bottom
        val childInContact: View = getChildInContact(parent, contactPoint) ?: return

        if (mListener.isHeader(parent.getChildAdapterPosition(childInContact))) {
            moveHeader(c, currentHeader, childInContact)
            return
        }

        drawHeader(c, currentHeader)
    }

    private fun getHeaderViewForItem(
        itemPosition: Int,
        parent: RecyclerView
    ): RecyclerView.ViewHolder {
        val headerPosition = mListener.getHeaderPositionForItem(itemPosition)
        val header = mListener.createHeader(
            parent.context,
            parent,
            mListener.getHeaderViewType(headerPosition)
        )
        mListener.bindHeader(header, headerPosition)
        return header
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
        c.save()
        c.translate(0f, (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.bottom > contactPoint) {
                if (child.top <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec =
            View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )
        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight.also {
            mStickyHeaderHeight = it
        })
    }


    interface StickyHeaderInterface<T, VH : RecyclerView.ViewHolder> {
        fun getHeaderPositionForItem(itemPosition: Int): Int

        fun createHeader(context: Context, parent: RecyclerView, viewType: Int): VH

        fun bindHeader(header: VH, headerPosition: Int)

        fun isHeader(itemPosition: Int): Boolean

        fun getHeaderViewType(headerPosition: Int): Int

        fun getHeaderItem(headerPosition: Int): T
    }
}
