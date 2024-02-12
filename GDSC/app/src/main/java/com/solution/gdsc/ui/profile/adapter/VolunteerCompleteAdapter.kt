package com.solution.gdsc.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.databinding.ItemRecordProceedBinding
import com.solution.gdsc.domain.model.response.RepairItem

class VolunteerCompleteAdapter(private val listener: RepairClickListener) : RecyclerView.Adapter<VolunteerCompleteAdapter.VolunteerCompleteViewHolder>()  {
    private val items = mutableListOf<RepairItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerCompleteViewHolder {
        return VolunteerCompleteViewHolder.from(parent, listener)
    }

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: VolunteerCompleteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(record: List<RepairItem>) {
        val diffUtil = VolunteerCompleteDiffUtil(items, record)
        val result = DiffUtil.calculateDiff(diffUtil)
        items.clear()
        items.addAll(record)
        result.dispatchUpdatesTo(this)
    }

    class VolunteerCompleteDiffUtil(
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

    class VolunteerCompleteViewHolder(
        private val binding: ItemRecordProceedBinding,
        private val listener: RepairClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepairItem) {
            binding.record = item
            binding.listener = listener
        }

        companion object {
            fun from(parent: ViewGroup, listener: RepairClickListener): VolunteerCompleteViewHolder {
                return VolunteerCompleteViewHolder(
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