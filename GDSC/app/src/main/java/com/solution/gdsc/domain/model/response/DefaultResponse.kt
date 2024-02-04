package com.solution.gdsc.domain.model.response

data class DefaultResponse(
    var status: Int = 0,
    var message: String = "",
    var data: Long = 0
)