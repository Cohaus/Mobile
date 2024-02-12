package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class RepairItem(
    @SerializedName("repair_id")
    val repairId: Long,
    val image: String,
    val title: String,
    val district: String,
    val date: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)
