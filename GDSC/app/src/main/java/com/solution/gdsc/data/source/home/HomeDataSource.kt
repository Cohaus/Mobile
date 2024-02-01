package com.solution.gdsc.data.source.home

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.CoHousService
import com.solution.gdsc.domain.model.response.DefaultResponse
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

        // imageFilePath는 이미지 파일에 해당하는 파일 경로
        val imageFile = File(imageFilePath)
        val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, imageRequestBody)

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
}