package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class SavedRecordDto(
    @SerializedName("record_id")
    val recordId: Long = 0,
    @SerializedName("user_id")
    val useId: String = "",
    val image: String = "",
    val title: String = "",
    val detail: String = "",
    val category: String = "",
    val grade: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("updated_at")
    val updatedAt: String = ""
)
