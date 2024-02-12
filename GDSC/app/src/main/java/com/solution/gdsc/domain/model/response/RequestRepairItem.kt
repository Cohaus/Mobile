package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class RequestRepairItem(
    @SerializedName("repair_id")
    val repairId: Long,
    val image: String,
    val title: String,
    val category: String,
    val district: String,
    @SerializedName("visit_date")
    val visitDate: String,
    @SerializedName("createdAt")
    val createAt: String
)
