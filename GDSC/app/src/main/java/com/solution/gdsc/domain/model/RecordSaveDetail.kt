package com.solution.gdsc.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecordSaveDetail(
    val title: String,
    val postImage: String,
    val aiContent: String,
    val location: String,
    val postedAt: String,
    val category: String
) : Parcelable
