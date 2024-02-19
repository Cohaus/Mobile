package com.solution.gdsc.data.source.home

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.CoHousService
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RepairIdResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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
    ): Flow<DefaultResponse> = flow {
        val titleRequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val detailRequestBody = detail.toRequestBody("text/plain".toMediaTypeOrNull())
        val gradeRequestBody = grade.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryRequestBody = category.toRequestBody("text/plain".toMediaTypeOrNull())

        val file = File(imageFilePath)
        val imageRequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, imageRequestBody)

        val response = coHousService.saveRecord(imagePart, titleRequestBody, detailRequestBody, gradeRequestBody, categoryRequestBody)
        emit(response)
    }.catch {
        Log.e(TAG, "Save Record Failure ${it.message.toString()}")
    }

    suspend fun postRepairBasicRecord(
        title: String, detail: String, category: String,
        placeId: String, address: String, district: String,
        date: String, image: String
    ): Flow<RepairIdResponse> = flow<RepairIdResponse> {
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

        val response = coHousService.postRepairBasicRecord(
            imagePart, titleRequestBody, detailRequestBody, categoryRequestBody,
            placeIdRequestBody, addressRequestBody, districtRequestBody, dateRequestBody
        )
        emit(response)
    }.catch {
        Log.e(TAG, "Post Repair Basic Failure ${it.message}")
    }
}