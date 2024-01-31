package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class UpdateUserInfoDto(
    @SerializedName("user_id")
    val userId: Long,
    val name: String,
    val id: String,
    val email: String,
    val tel: String
)