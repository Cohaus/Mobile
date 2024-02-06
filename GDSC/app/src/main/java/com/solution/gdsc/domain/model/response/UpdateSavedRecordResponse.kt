package com.solution.gdsc.domain.model.response

data class UpdateSavedRecordResponse(
    val status: Int = 0,
    val message: String = "",
    val data: SavedRecordResponse? = null
)
