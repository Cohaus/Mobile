package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class RepairId(
    @SerializedName("record_id")
    val recordId: Long,
    @SerializedName("repair_id")
    val repairId: Long
)
