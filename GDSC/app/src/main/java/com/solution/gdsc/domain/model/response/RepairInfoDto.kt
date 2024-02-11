package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class RepairInfoDto(
    @SerializedName("repair_status")
    val repairStatus: String,
    val category: String,
    @SerializedName("request_date")
    val requestDate: String,
    @SerializedName("proceed_date")
    val proceedDate: String?,
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_tel")
    val userTel: String,
    @SerializedName("volunteer_id")
    val volunteerId: Long,
    @SerializedName("volunteer_name")
    val volunteerName: String,
    @SerializedName("volunteer_tel")
    val volunteerTel: String,
    val date: String,
    val address: String
)
