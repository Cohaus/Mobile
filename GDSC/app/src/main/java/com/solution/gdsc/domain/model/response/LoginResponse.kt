package com.solution.gdsc.domain.model.response

data class LoginResponse(
    val status: Int = 0,
    val message: String = "",
    val data: LoginDto?
)