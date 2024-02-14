package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class WasteFacilities(
    @SerializedName("waste_facilities")
    val wasteFacilities: WasteFacilityDto
)
