package com.solution.gdsc.domain.model.response

data class VolunteerRegistrationResponse (
    val status: Int = 0,
    val message: String = "",
    val data: VolunteerInfo?
)