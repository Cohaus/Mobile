package com.solution.gdsc.domain.model.response

data class SignUpResponse(
    val accessToken: String,
    val refreshToken: String
)