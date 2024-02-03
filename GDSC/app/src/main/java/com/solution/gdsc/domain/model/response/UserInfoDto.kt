package com.solution.gdsc.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoDto(
    @SerializedName("user_id")
    val userId: Long = 0,
    val name: String = "",
    val id: String = "",
    val tel: String = "",
    val email: String = "",
    @SerializedName("volunteer_type")
    val volunteerType: String? = null,
    @SerializedName("organization_name")
    val organizationName: String? = null
) : Parcelable
