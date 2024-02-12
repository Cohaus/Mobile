package com.solution.gdsc.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.databinding.ItemRecordProceedBinding
import com.solution.gdsc.domain.model.response.RepairItem

class VolunteerProceedAdapter(private val listener: RepairClickListener) : RecyclerView.Adapter<VolunteerProceedAdapter.VolunteerProceedViewHolder>()  {
    private val items = mutableListOf<RepairItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerProceedViewHolder {
        return VolunteerProceedViewHolder.from(parent, listener)
    }

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: VolunteerProceedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(record: List<RepairItem>) {
        val diffUtil = VolunteerProceedDiffUtil(items, record)
        val result = DiffUtil.calculateDiff(diffUtil)
        items.clear()
        items.addAll(record)
        result.dispatchUpdatesTo(this)
    }

    class VolunteerProceedDiffUtil(
        private val oldItems: List<RepairItem>,
        private val newItems: List<RepairItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return oldItem.repairId == newItem.repairId
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }

    }

    class VolunteerProceedViewHolder(
        private val binding: ItemRecordProceedBinding,
        private val listener: RepairClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepairItem) {
            binding.record = item
            binding.listener = listener
        }

        companion object {
            fun from(parent: ViewGroup, listener: RepairClickListener): VolunteerProceedViewHolder {
                return VolunteerProceedViewHolder(
                    ItemRecordProceedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    listener
                )
            }
        }
    }
}