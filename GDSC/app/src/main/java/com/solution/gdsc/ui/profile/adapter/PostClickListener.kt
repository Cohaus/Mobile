package com.solution.gdsc.ui.profile.adapter

import com.solution.gdsc.domain.model.response.RecordItem

interface PostClickListener {
    fun onPostClick(post: RecordItem)
}