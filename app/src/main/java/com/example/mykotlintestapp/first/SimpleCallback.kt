package com.example.mykotlintestapp.first

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlintestapp.R

class SimpleCallback(
    private val recyclerView: RecyclerView,
    private val adapter: LoginListAdapter,private val onDeleteClickListener: (position: Int) -> Unit
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT
) {
    private val MAX_SWIPE_DISTANCE_THRESHOLD = 0.1f
    private var isSwiping = false

    private val deleteIcon = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)?.let { drawable ->
        DrawableCompat.setTint(drawable, Color.RED)
        drawable
    }


    override fun isLongPressDragEnabled(): Boolean {
        Log.d("simpleCallbackSwipe", "isLongPressDragEnabled ... ")
        return true
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // Notify the adapter of the move
        Log.d("simpleCallbackSwipe", "Item Moving ... ")
        adapter.onItemMove(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
        return true
    }


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {

        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.LEFT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

//    override fun onMove(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        target: RecyclerView.ViewHolder
//    ): Boolean {
//        val from = viewHolder.adapterPosition
//        val to = target.adapterPosition
//        _dataset?.toMutableList()?.let { Collections.swap(it,from,to) }
//        recyclerView.getAdapter()?.notifyItemMoved(from, to);
//        Log.d("simpleCallback","from "+from+ " to "+to)
//        return true
//    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//        adapter.onItemDismiss(viewHolder.bindingAdapterPosition)
        adapter.notifyItemChanged(viewHolder.absoluteAdapterPosition)
        val swipedItem =
            adapter.getItem(viewHolder.absoluteAdapterPosition) // Assuming you have a method to get the item at a given position
        swipedItem.isSwiped = true
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {

        return MAX_SWIPE_DISTANCE_THRESHOLD * recyclerView.width
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if ((viewHolder as LoginListAdapter.ViewHolder).listSubItem.isVisible)
            return

        val itemView = viewHolder.itemView
        val iconMargin =
            recyclerView.context.resources.getDimension(R.dimen.delete_icon_margin).toInt()
        val iconSize = recyclerView.context.resources.getDimension(R.dimen.delete_icon_size).toInt()
        val iconTop = itemView.top + (itemView.height - iconSize) / 2
        val iconBottom = iconTop + iconSize
        var constrainedDx = 0f

        val limit = MAX_SWIPE_DISTANCE_THRESHOLD * itemView.width

        if (dX < 0) { // Swiping to the left
            val iconRight = itemView.right - iconMargin
            val iconLeft = itemView.right - iconMargin - iconSize
            constrainedDx = -1 * limit
            deleteIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        }
        if (dX < constrainedDx) deleteIcon?.draw(c)
        super.onChildDraw(c, recyclerView, viewHolder, if (dX > constrainedDx) dX else constrainedDx, dY, actionState, isCurrentlyActive)
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        isSwiping = actionState == ItemTouchHelper.ACTION_STATE_SWIPE
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        // Prevent default animation to return the view to its original position
        viewHolder.itemView.translationX = 0f
        deleteIcon?.callback = null

    }

}
