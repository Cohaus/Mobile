package com.solution.gdsc.domain.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepairRecordDto(
    @SerializedName("record_id")
    val recordId: Long,
    val type: String,
    val status: String?,
    @SerializedName("user_id")
    val userId: String,
    val image: String,
    val title: String,
    val detail: String,
    val district: String,
    val grade: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updateAt: String
) : Parcelable
