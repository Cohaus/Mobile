package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class UserRecordListResponse(
    @SerializedName("repair_record")
    val repairRecord: RepairRecord,
    @SerializedName("saved_record")
    val savedRecord: SaveRecord
)