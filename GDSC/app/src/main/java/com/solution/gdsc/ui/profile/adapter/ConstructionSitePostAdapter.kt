package com.solution.gdsc.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.data.model.ConstPost
import com.solution.gdsc.data.model.ConstructionSitePost
import com.solution.gdsc.databinding.ItemConstructionSitePostBinding

class ConstructionSitePostAdapter(
    private val listener: PostClickListener
) : RecyclerView.Adapter<ConstructionSitePostAdapter.ConstructionSitePostViewHolder>() {
    private val items = mutableListOf<ConstPost>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConstructionSitePostViewHolder {
        return ConstructionSitePostViewHolder.from(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ConstructionSitePostViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun add(posts: List<ConstPost>) {
        items.clear()
        items.addAll(posts)
        notifyDataSetChanged()
    }

    class ConstructionSitePostViewHolder(
        private val binding: ItemConstructionSitePostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: ConstPost, listener: PostClickListener) {
             binding.ivConstructionSitePostImage.setOnClickListener {
                 listener.onPostClick(post.category.title, post.post)
             }
            binding.tvConstructionSitePostDate.text = post.post.postedAt
            binding.tvConstructionSitePostLocation.text = post.post.location
        }

        companion object {
            fun from(parent: ViewGroup): ConstructionSitePostViewHolder {
                return ConstructionSitePostViewHolder(
                    ItemConstructionSitePostBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}