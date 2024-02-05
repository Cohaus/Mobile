package com.solution.gdsc.domain.model.response

data class DeleteSavedRecordResponse(
    val status: Int,
    val message: String,
    val data: Long
)
