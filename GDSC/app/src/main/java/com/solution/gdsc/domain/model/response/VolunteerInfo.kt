package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class VolunteerInfo(
    val type: String,
    @SerializedName("organization_name")
    val organizationName: String?
)