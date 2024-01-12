package com.solution.gdsc.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solution.gdsc.data.model.ConstPost
import com.solution.gdsc.data.model.ConstructionItem
import com.solution.gdsc.data.model.ConstructionSiteCategory
import com.solution.gdsc.data.model.ConstructionSiteCategoryPost
import com.solution.gdsc.databinding.ItemConstructionSiteCategoryBinding
import com.solution.gdsc.databinding.ItemConstructionSiteCategoryPostBinding

private const val VIEW_TYPE_SECTION_TITLE = 0
private const val VIEW_TYPE_SECTION_ARTICLE = 1

class ConstructionSiteListAdapter(
    private val listener: PostClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<ConstructionItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SECTION_TITLE -> ConstructionSiteCategoryViewHolder.from(parent)
            else -> ConstructionSiteCategoryPostViewHolder.from(parent, listener)
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ConstructionSiteCategoryViewHolder -> {
                val item = items[position] as ConstructionSiteCategory
                holder.bind(item)
            }

            is ConstructionSiteCategoryPostViewHolder -> {
                val item = items[position] as ConstructionSiteCategoryPost
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ConstructionSiteCategory -> VIEW_TYPE_SECTION_TITLE
            is ConstructionSiteCategoryPost -> VIEW_TYPE_SECTION_ARTICLE
        }
    }

    fun addPosts(posts: List<ConstPost>) {
        val items = mutableListOf<ConstructionItem>()
        posts.groupBy { it.category.title }
            .forEach {
                items.add(ConstructionSiteCategory(it.key))
                items.add(ConstructionSiteCategoryPost(it.value))
            }
        update(items)
    }

    private fun update(bookmarkItems: List<ConstructionItem>) {
        items.clear()
        items.addAll(bookmarkItems)
        notifyDataSetChanged()
    }

    class ConstructionSiteCategoryViewHolder(
        private val binding: ItemConstructionSiteCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ConstructionSiteCategory) {
            binding.tvConstructionSiteCategory.text = item.title
        }

        companion object {
            fun from(parent: ViewGroup): ConstructionSiteCategoryViewHolder {
                return ConstructionSiteCategoryViewHolder(
                    ItemConstructionSiteCategoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    class ConstructionSiteCategoryPostViewHolder(
        private val binding: ItemConstructionSiteCategoryPostBinding,
        listener: PostClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private val nestedAdapter = ConstructionSitePostAdapter(listener)

        init {
            binding.rvConstructionSiteCategoryPostList.adapter = nestedAdapter
        }

        fun bind(item: ConstructionSiteCategoryPost) {
            nestedAdapter.add(item.post)
        }

        companion object {
            fun from(parent: ViewGroup, listener: PostClickListener): ConstructionSiteCategoryPostViewHolder {
                return ConstructionSiteCategoryPostViewHolder(
                    ItemConstructionSiteCategoryPostBinding.inflate(
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