package com.solution.gdsc.data.source.home

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.CoHousService
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RepairId
import com.solution.gdsc.domain.model.response.RepairIdResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val coHousService: CoHousService
) {
    suspend fun saveRecord(
        title: String, detail: String, grade: String, category: String,
        imageFilePath: String
    ): DefaultResponse {
        val titleRequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val detailRequestBody = detail.toRequestBody("text/plain".toMediaTypeOrNull())
        val gradeRequestBody = grade.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryRequestBody = category.toRequestBody("text/plain".toMediaTypeOrNull())

        val file = File(imageFilePath)
        val imageRequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, imageRequestBody)

        var response = DefaultResponse(200, "성공", 1)
        withContext(Dispatchers.IO) {
            runCatching {
                coHousService.saveRecord(imagePart, titleRequestBody, detailRequestBody, gradeRequestBody, categoryRequestBody)
            }.onSuccess {
                response = it
            }.onFailure {
                Log.e(TAG, "Save Record Failure", it)
                // 에러 처리 로직 추가
            }
        }

        return response
    }

    suspend fun postRepairBasicRecord(
        title: String, detail: String, category: String,
        placeId: String, address: String, district: String,
        date: String, image: String
    ): RepairIdResponse {
        Log.e("Post Repairs", "$title, $detail, $category, $placeId, $address, $district, $date")
        val titleRequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val detailRequestBody = detail.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryRequestBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
        val placeIdRequestBody = placeId.toRequestBody("text/plain".toMediaTypeOrNull())
        val addressRequestBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val districtRequestBody = district.toRequestBody("text/plain".toMediaTypeOrNull())
        val dateRequestBody = date.toRequestBody("text/plain".toMediaTypeOrNull())

        val file = File(image)
        val imageRequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, imageRequestBody)
        Log.e("PostRepairs", "$titleRequestBody, $detailRequestBody, $categoryRequestBody, $placeIdRequestBody, $addressRequestBody, $districtRequestBody, $dateRequestBody")
        var response = RepairIdResponse(1, "성공",
            RepairId(1, 1)
        )
        withContext(Dispatchers.IO) {
            runCatching {
                coHousService.postRepairBasicRecord(
                    imagePart, titleRequestBody, detailRequestBody, categoryRequestBody,
                    placeIdRequestBody, addressRequestBody, districtRequestBody, dateRequestBody
                )
            }.onSuccess {
                response = it
            }.onFailure {
                Log.e(TAG, "Post Repair Basic Failure ${it.message}")
            }
        }
        return response
    }
}