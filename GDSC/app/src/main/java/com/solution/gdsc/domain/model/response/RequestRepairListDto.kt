package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class RequestRepairListDto(
    @SerializedName("request_repairs")
    val requestRepairs: RequestRepairDto
)
