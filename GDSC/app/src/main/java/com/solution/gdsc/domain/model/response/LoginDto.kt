package com.solution.gdsc.domain.model.response

data class LoginDto(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)
