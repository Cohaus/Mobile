package com.solution.gdsc.domain.model.response

import com.google.gson.annotations.SerializedName

data class VolunteerRepairListDto(
    @SerializedName("proceeding_repair")
    val proceedingRepair: ProceedingRepair,
    @SerializedName("complete_repair")
    val completeRepair: CompleteRepair
)
