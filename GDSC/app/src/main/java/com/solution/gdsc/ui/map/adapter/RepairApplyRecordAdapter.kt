package com.solution.gdsc.ui.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.databinding.ItemRepairApplyRecordBinding
import com.solution.gdsc.domain.model.response.RequestRepairItem
import com.solution.gdsc.ui.profile.adapter.RepairClickListener

class RepairApplyRecordAdapter(
    private val listener: RepairClickListener
) : RecyclerView.Adapter<RepairApplyRecordAdapter.RepairApplyRecordViewHolder>() {

    private val items = mutableListOf<RequestRepairItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairApplyRecordViewHolder {
        return RepairApplyRecordViewHolder.from(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepairApplyRecordViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun update(records: List<RequestRepairItem>) {
        val diffUtil = RepairApplyRecordDiffUtil(items, records)
        val result = DiffUtil.calculateDiff(diffUtil)
        items.clear()
        items.addAll(records)
        result.dispatchUpdatesTo(this)
    }

    class RepairApplyRecordDiffUtil(
        private val oldItems: List<RequestRepairItem>,
        private val newItems: List<RequestRepairItem>
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

    class RepairApplyRecordViewHolder(
        private val binding: ItemRepairApplyRecordBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(record: RequestRepairItem, listener: RepairClickListener) {
            binding.requestRepairItem = record
            binding.listener = listener
        }

        companion object {
            fun from(parent: ViewGroup): RepairApplyRecordViewHolder {
                return RepairApplyRecordViewHolder(
                    ItemRepairApplyRecordBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}