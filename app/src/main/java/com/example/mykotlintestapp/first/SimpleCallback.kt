package com.example.mykotlintestapp.first

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class SimpleCallback(private val adapter: LoginListAdapter) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    override fun isLongPressDragEnabled(): Boolean {
        Log.d("simpleCallbackSwipe","isLongPressDragEnabled ... ")
        return true
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // Notify the adapter of the move
        Log.d("simpleCallbackSwipe","Item Moving ... ")
        adapter.onItemMove(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
        return true
    }



    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {

        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
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
        Log.d("simpleCallbackSwipe","from $direction")
        adapter.onItemDismiss(viewHolder.bindingAdapterPosition)
    }

}
