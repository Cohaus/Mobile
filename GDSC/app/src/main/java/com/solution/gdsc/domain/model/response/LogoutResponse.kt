package com.solution.gdsc.domain.model.response

data class LogoutResponse(
    var status: Int = 0,
    var message: String = "",
    var data: Nothing? = null
)