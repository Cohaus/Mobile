package com.solution.gdsc.ui.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.databinding.ItemRepairApplyRecordBinding
import com.solution.gdsc.domain.model.response.RecordItem
import com.solution.gdsc.ui.profile.adapter.PostClickListener

class RepairApplyRecordAdapter(
    private val listener: PostClickListener
) : RecyclerView.Adapter<RepairApplyRecordAdapter.RepairApplyRecordViewHolder>() {

    private val items = mutableListOf<RecordItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairApplyRecordViewHolder {
        return RepairApplyRecordViewHolder.from(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepairApplyRecordViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun add(records: List<RecordItem>) {
        val positionStart = items.size
        items.addAll(records)
        notifyItemRangeInserted(positionStart, records.size)
    }

    class RepairApplyRecordViewHolder(
        private val binding: ItemRepairApplyRecordBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(record: RecordItem, listener: PostClickListener) {
            binding.btnItemRecordMore.setOnClickListener {
                listener.onPostClick(record)
            }
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