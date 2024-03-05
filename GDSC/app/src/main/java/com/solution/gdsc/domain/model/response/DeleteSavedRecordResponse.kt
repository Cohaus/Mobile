package com.solution.gdsc.domain.model.response

data class DeleteSavedRecordResponse(
    val status: Int = 0,
    val message: String = "",
    val data: Long = 0
)
