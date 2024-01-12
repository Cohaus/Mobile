package com.solution.gdsc.ui.profile.adapter

import com.solution.gdsc.data.model.ConstructionSitePost

interface PostClickListener {
    fun onPostClick(category: String, post: ConstructionSitePost)
}