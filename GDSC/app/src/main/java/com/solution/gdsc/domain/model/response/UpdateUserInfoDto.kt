package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class UpdateUserInfoDto(
    @SerializedName("user_id")
    val userId: Long,
    val name: String,
    val id: String,
    val tel: String,
    @SerializedName("volunteer_type")
    val volunteerType: String?,
    @SerializedName("organization_name")
    val organizationName: String?
)