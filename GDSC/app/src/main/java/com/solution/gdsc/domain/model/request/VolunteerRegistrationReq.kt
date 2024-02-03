package com.solution.gdsc.domain.model.request

import com.google.gson.annotations.SerializedName

data class VolunteerRegistrationReq(
    val type: String,
    @SerializedName("organization_name")
    val organizationName: String?
)
