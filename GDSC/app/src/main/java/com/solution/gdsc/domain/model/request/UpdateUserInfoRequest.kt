package com.solution.gdsc.domain.model.request

data class UpdateUserInfoRequest(
    val id: String,
    val name: String,
    val tel: String,
    val email: String,
)
