package com.solution.gdsc.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.data.model.RecordSaveDetail
import com.solution.gdsc.databinding.ItemRecordSaveBinding

class RecordSaveApter(
    private val listener: PostClickListener
) : RecyclerView.Adapter<RecordSaveApter.RecordSaveViewHolder>() {
    private val items = mutableListOf<RecordSaveDetail>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordSaveViewHolder {
        return RecordSaveViewHolder.from(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecordSaveViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun add(posts: List<RecordSaveDetail>) {
        val positionStart = items.size
        items.addAll(posts)
        notifyItemRangeInserted(positionStart, posts.size)
    }

    class RecordSaveViewHolder(
        private val binding: ItemRecordSaveBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: RecordSaveDetail, listener: PostClickListener) {
             binding.ivConstructionSitePostImage.setOnClickListener {
                 listener.onPostClick(post)
             }
            binding.tvRecordSaveDate.text = post.postedAt
            binding.tvRecordSaveLocation.text = post.location
        }

        companion object {
            fun from(parent: ViewGroup): RecordSaveViewHolder {
                return RecordSaveViewHolder(
                    ItemRecordSaveBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}