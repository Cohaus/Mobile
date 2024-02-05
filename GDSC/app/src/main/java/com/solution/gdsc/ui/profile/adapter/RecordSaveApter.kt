package com.solution.gdsc.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.databinding.ItemRecordSaveBinding
import com.solution.gdsc.domain.model.response.RecordItem
import com.solution.gdsc.ui.extensions.load

class RecordSaveApter(
    private val listener: PostClickListener
) : RecyclerView.Adapter<RecordSaveApter.RecordSaveViewHolder>() {
    private val items = mutableListOf<RecordItem>()

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

    fun update(posts: List<RecordItem>) {
        items.clear()
        items.addAll(posts)
        notifyDataSetChanged()
    }

    class RecordSaveViewHolder(
        private val binding: ItemRecordSaveBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: RecordItem, listener: PostClickListener) {
             binding.ivConstructionSitePostImage.setOnClickListener {
                 listener.onPostClick(post)
             }
            binding.ivConstructionSitePostImage.load(post.image)
            binding.tvRecordSaveDate.text = post.createdAt
            binding.tvRecordSaveLocation.text = post.title
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