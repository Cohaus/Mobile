package com.solution.gdsc.domain.model.request

import com.google.gson.annotations.SerializedName

data class UpdateUserInfoRequest(
    val id: String,
    val name: String,
    val tel: String,
    val email: String,
    @SerializedName("user_authority")
    val userAuthority: String,
    @SerializedName("volunteer_type")
    val volunteerType: String?,
    @SerializedName("organization_name")
    val organization: String?
)
