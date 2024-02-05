package com.solution.gdsc.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.databinding.ItemRecordSaveBinding
import com.solution.gdsc.domain.model.response.RecordItem

class RecordSaveApter(
    private val listener: PostClickListener
) : RecyclerView.Adapter<RecordSaveApter.RecordSaveViewHolder>() {
    private val items = mutableListOf<RecordItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordSaveViewHolder {
        return RecordSaveViewHolder.from(parent, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecordSaveViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(posts: List<RecordItem>) {
        val diffUtil = RecordSaveDiffUtil(items, posts)
        val result = DiffUtil.calculateDiff(diffUtil)
        items.clear()
        items.addAll(posts)
        result.dispatchUpdatesTo(this)
    }

    class RecordSaveDiffUtil(
        private val oldItems: List<RecordItem>,
        private val newItems: List<RecordItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return oldItem.recordId == newItem.recordId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }

    }

    class RecordSaveViewHolder(
        private val binding: ItemRecordSaveBinding,
        private val listener: PostClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: RecordItem) {
             binding.listener = listener
            binding.record = post
        }

        companion object {
            fun from(parent: ViewGroup, listener: PostClickListener): RecordSaveViewHolder {
                return RecordSaveViewHolder(
                    ItemRecordSaveBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                    ,
                    listener
                )
            }
        }
    }
}