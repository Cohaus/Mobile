package com.solution.gdsc.ui.profile.adapter

import com.solution.gdsc.data.model.RecordSaveDetail

interface PostClickListener {
    fun onPostClick(post: RecordSaveDetail)
}