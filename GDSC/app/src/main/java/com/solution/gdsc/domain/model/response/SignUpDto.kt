package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class SignUpDto(
    @SerializedName("user_id")
    val userId: Long,
    val name: String
)
