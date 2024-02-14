package com.solution.gdsc.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.databinding.ItemWasteFacilityItemBinding
import com.solution.gdsc.domain.model.response.WasteFacilityItem

class WasteFacilityAdapter : RecyclerView.Adapter<WasteFacilityAdapter.WasteFacilityViewHolder>() {
    private val items = mutableListOf<WasteFacilityItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WasteFacilityViewHolder {
        return WasteFacilityViewHolder.from(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: WasteFacilityViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun add(wasteFacilityItems: List<WasteFacilityItem>) {
        items.clear()
        items.addAll(wasteFacilityItems)
        notifyDataSetChanged()
    }

    class WasteFacilityViewHolder(
        private val binding: ItemWasteFacilityItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WasteFacilityItem) {
            binding.wasteFacilityItem = item
        }

        companion object {
            fun from(parent: ViewGroup): WasteFacilityViewHolder {
                return WasteFacilityViewHolder(
                    ItemWasteFacilityItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}