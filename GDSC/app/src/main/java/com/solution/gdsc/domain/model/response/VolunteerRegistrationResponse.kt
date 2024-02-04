package com.solution.gdsc.domain.model.response

data class VolunteerRegistrationResponse (
    val status: Int,
    val message: String,
    val data: VolunteerInfo
)