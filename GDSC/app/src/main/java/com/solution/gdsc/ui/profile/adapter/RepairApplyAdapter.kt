package com.solution.gdsc.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.domain.model.RecordSaveDetail
import com.solution.gdsc.databinding.ItemRepairApplyBinding

class RepairApplyAdapter(
    private val listener: PostClickListener
) : RecyclerView.Adapter<RepairApplyAdapter.RepairApplyViewHolder>() {
    private val items = mutableListOf<RecordSaveDetail>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepairApplyViewHolder {
        return RepairApplyViewHolder.from(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepairApplyViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun add(posts: List<RecordSaveDetail>) {
        val positionStart = items.size
        items.addAll(posts)
        notifyItemRangeInserted(positionStart, posts.size)
    }

    class RepairApplyViewHolder(
        private val binding: ItemRepairApplyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: RecordSaveDetail, listener: PostClickListener) {
             binding.ivConstructionSitePostImage.setOnClickListener {
                 listener.onPostClick(post)
             }
            binding.tvRepairApplyDate.text = post.postedAt
            binding.tvRepairLocation.text = post.location
        }

        companion object {
            fun from(parent: ViewGroup): RepairApplyViewHolder {
                return RepairApplyViewHolder(
                    ItemRepairApplyBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}