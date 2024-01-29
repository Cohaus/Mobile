package com.solution.gdsc.domain.model.response

data class LoginResponse(
    val status: Int,
    val message: String,
    val data: LoginDto
)