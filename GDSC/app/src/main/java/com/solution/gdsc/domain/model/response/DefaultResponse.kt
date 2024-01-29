package com.solution.gdsc.domain.model.response

data class DefaultResponse(
    var status: Int,
    var message: String,
    var data: Long
)