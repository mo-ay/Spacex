package com.ayprotech.spacex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayprotech.spacex.R
import com.ayprotech.spacex.data.network.responses.launche.LaunchesItem
import com.ayprotech.spacex.databinding.LaunchItemBinding


class LaunchesAdapter(val launchClick: LaunchClick) :
    ListAdapter<LaunchesItem, RecyclerView.ViewHolder>(LaunchDiff()) {


    var launchItems: List<LaunchesItem> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify LaunchesItem registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            submitList(launchItems)
        }

    override fun getItemCount(): Int {
        return launchItems.size
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesItemViewHolder {

        val withDataBinding: LaunchItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            LaunchesItemViewHolder.LAYOUT,
            parent,
            false
        )
        return LaunchesItemViewHolder(withDataBinding)
    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < launchItems.size)
            (holder as LaunchesItemViewHolder).viewDataBinding.also {
                it.launchItem = getItem(position)
                it.launchClick = launchClick
            }
    }

}

/**
 * ViewHolder for DevByte items. All work is done by data binding.
 */
class LaunchesItemViewHolder(val viewDataBinding: LaunchItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.launch_item
    }
}


class LaunchDiff : DiffUtil.ItemCallback<LaunchesItem>() {
    override fun areItemsTheSame(oldItem: LaunchesItem, newItem: LaunchesItem): Boolean {
        return oldItem.date_unix == newItem.date_unix
    }

    override fun areContentsTheSame(oldItem: LaunchesItem, newItem: LaunchesItem): Boolean {
        return oldItem == newItem
    }
}

class LaunchClick(val block: (String) -> Unit) {
    /**
     * Called when a LaunchesItem is clicked
     *
     * @param rocketId the LaunchesItem that was clicked
     */
    fun onClick(rocketId: String) = block(rocketId)
}