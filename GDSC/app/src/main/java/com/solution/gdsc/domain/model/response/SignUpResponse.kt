package com.solution.gdsc.domain.model.response

data class SignUpResponse(
    val status: Int = 0,
    val message: String = "",
    val data: SignUpDto?
)