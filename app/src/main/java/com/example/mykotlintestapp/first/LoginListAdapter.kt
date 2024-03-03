package com.example.mykotlintestapp.first

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        holder.name.text = _dataset[position].name
        holder.role.text = _dataset[position].role
        holder.time.text = _dataset[position].loginTime.toString()


        holder.itemView.setOnClickListener {
            clickListener?.onClick(_dataset[position])
        }

        holder.itemView.setOnLongClickListener {
            clickListener?.onLongClick(_dataset[position])
            Log.d("simpleCallbackSwipe", "Long Click Listener ... ")
            return@setOnLongClickListener true
        }
        if (position % 2 == 0) {
            holder.itemView.setBackground(
                ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.border_even
                )
            );
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(
//                holder.itemView.context,
//                R.color.primaryLightColor
//            ));
        } else {
            holder.itemView.setBackground(
                ContextCompat.getDrawable(
                    holder.itemView.context,
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


}