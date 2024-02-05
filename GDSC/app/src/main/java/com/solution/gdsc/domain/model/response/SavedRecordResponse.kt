package com.solution.gdsc.domain.model.response

data class SavedRecordResponse(
    val status: Long,
    val message: String,
    val data: SavedRecordDto
)
