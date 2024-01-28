package com.solution.gdsc.ui.profile.adapter

import com.solution.gdsc.domain.model.RecordSaveDetail

interface PostClickListener {
    fun onPostClick(post: RecordSaveDetail)
}