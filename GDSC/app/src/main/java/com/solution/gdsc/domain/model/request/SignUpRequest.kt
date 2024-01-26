package com.solution.gdsc.domain.model.request

data class SignUpRequest(
    var id: String,
    var password: String,
    var name: String,
    var tel: String,
    var email: String
)