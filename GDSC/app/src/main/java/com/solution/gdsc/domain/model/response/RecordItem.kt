package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class RecordItem(
    @SerializedName("record_id")
    val recordId: Long,
    val type: String,
    val image: String,
    val title: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updateAt: String
)
