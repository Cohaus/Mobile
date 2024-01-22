package com.solution.gdsc.domain.model.response

data class LoginResponse(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String
)