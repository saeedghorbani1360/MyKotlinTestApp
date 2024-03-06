package com.example.mykotlintestapp.first

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlintestapp.R
import com.example.mykotlintestapp.databinding.LoginHistItemBinding
import java.util.Collections

class LoginListAdapter(dataSet: Array<LoginInfo>) :
    RecyclerView.Adapter<LoginListAdapter.ViewHolder>() {

    private var _dataset = dataSet
    private var clickListener: OnClickListener? = null

    class ViewHolder(view: LoginHistItemBinding) : RecyclerView.ViewHolder(view.root) {
        val name: TextView
        val role: TextView
        val time: TextView
        val listItem: ConstraintLayout = view.listItem
        val listSubItem: ConstraintLayout = view.listSubItem
        val delete: Button = view.delete



        init {
            name = view.uname
            role = view.urole
            time = view.loginTime
        }

    }
    fun onItemDismiss(position: Int) {
        // Remove the item from your dataset
        val mutableList = _dataset.toMutableList()
        // Remove the item from the list
        mutableList.removeAt(position)
        // Convert back to array
        _dataset = mutableList.toTypedArray()
        // Notify the adapter that the item has been removed
        notifyItemRemoved(position)
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        // Swap the items in your data set
        _dataset.toMutableList().apply {
            Collections.swap(this, fromPosition, toPosition) }
            .toTypedArray()

        // Notify the adapter that the item has moved
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LoginHistItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = _dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = _dataset[position]
        holder.name.text = _dataset[position].name
        holder.role.text = _dataset[position].role
        holder.time.text = _dataset[position].loginTime.toString()


        holder.delete.setOnClickListener {
            clickListener?.onClick(_dataset[position])
        }

        holder.itemView.setOnClickListener {
            if (holder.listSubItem.isVisible) {
                val fadeOut = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fade_out)
                holder.listSubItem.startAnimation(fadeOut)
                holder.listSubItem.visibility = View.GONE
            } else {
                val fadeIn = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fade_in)
                holder.listSubItem.startAnimation(fadeIn)
                holder.listSubItem.visibility = View.VISIBLE
            }
        }

        holder.itemView.setOnLongClickListener {
            clickListener?.onLongClick(_dataset[position])
            Log.d("simpleCallbackSwipe", "Long Click Listener ... ")
            return@setOnLongClickListener true
        }
        if (position % 2 == 0) {
            holder.listItem.setBackground(
                ContextCompat.getDrawable(
                    holder.listItem.context,
                    R.drawable.border_even
                )
            );
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(
//                holder.itemView.context,
//                R.color.primaryLightColor
//            ));
        } else {
            holder.listItem.setBackground(
                ContextCompat.getDrawable(
                    holder.listItem.context,
                    R.drawable.border_odd
                )
            );
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(
//                holder.itemView.context,
//                R.color.primaryDarkColor
//            ));
        }
    }

    fun reloadData(ds: Array<LoginInfo>) {
        _dataset = ds
        notifyDataSetChanged()
    }

    fun setOnClickListener(clickListener: OnClickListener) {
        this.clickListener = clickListener

    }

    interface OnClickListener {
        fun onClick(item: LoginInfo)
        fun onLongClick(item: LoginInfo)

    }
    fun getItem(itemId : Int): LoginInfo {
        return _dataset[itemId]
    }


}