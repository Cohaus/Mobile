package com.solution.gdsc.data.remote

import com.solution.gdsc.domain.model.request.UpdateSavedRecordReq
import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.request.VolunteerRegistrationReq
import com.solution.gdsc.domain.model.response.CountRepairResponse
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.DeleteSavedRecordResponse
import com.solution.gdsc.domain.model.response.RepairIdResponse
import com.solution.gdsc.domain.model.response.RepairInfoResponse
import com.solution.gdsc.domain.model.response.RepairRecordResponse
import com.solution.gdsc.domain.model.response.RequestRepairListResponse
import com.solution.gdsc.domain.model.response.SavedRecordResponse
import com.solution.gdsc.domain.model.response.UpdateSavedRecordResponse
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import com.solution.gdsc.domain.model.response.UserRecordResponse
import com.solution.gdsc.domain.model.response.VolunteerRegistrationResponse
import com.solution.gdsc.domain.model.response.VolunteerRepairListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface CoHousService {
    // Profile
    @DELETE("/auth/logout")
    suspend fun logout(): DefaultResponse

    @GET("/my-page")
    suspend fun getUserInfo(): UserInfoResponse

    @PATCH("/my-page/info")
    suspend fun updateUserInfo(@Body updateUserInfoRequest: UpdateUserInfoRequest): UpdateUserInfoResponse

    @DELETE("my-page/withdraw")
    suspend fun withdraw(): DefaultResponse

    @GET("/my-page/records")
    suspend fun getUserRecord(): UserRecordResponse
    @GET("/records/{recordId}")
    suspend fun getRecordInfo(@Path("recordId") recordId: Long): SavedRecordResponse
    @DELETE("/records/{recordId}")
    suspend fun deleteSavedRecord(@Path("recordId") recordId: Long): DeleteSavedRecordResponse
    @PATCH("/records/{recordId}")
    suspend fun updateSavedRecord(@Path("recordId") recordId: Long, @Body updateSavedRecordDto: UpdateSavedRecordReq): UpdateSavedRecordResponse

    @GET("/repairs/{repairId}")
    suspend fun getRepairsRecord(@Path("repairId") repairId: Long): RepairRecordResponse
    @GET("/repairs/{repairId}/info")
    suspend fun getRepairInfo(@Path("repairId") repairId: Long): RepairInfoResponse

    @PUT("/volunteers/users")
    suspend fun putVolunteerUser(@Body volunteerRegistrationReq: VolunteerRegistrationReq): VolunteerRegistrationResponse
    @GET("/volunteers/repairs")
    suspend fun getVolunteerRepairList(): VolunteerRepairListResponse

    // Home
    @Multipart
    @POST("/records")
    suspend fun saveRecord(
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part("grade") grade: RequestBody,
        @Part("category") category: RequestBody
    ): DefaultResponse

    @Multipart
    @POST("/repairs/basic")
    suspend fun postRepairBasicRecord(
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part("category") category: RequestBody,
        @Part("place_id") placeId: RequestBody,
        @Part("address") address: RequestBody,
        @Part("district") district: RequestBody,
        @Part("date") date: RequestBody
    ): RepairIdResponse

    // Map
    @GET("/map")
    suspend fun getAllRepairRecord(): CountRepairResponse
    @GET("/map/districts/{districtId}")
    suspend fun getRequestRepairList(@Path("districtId") districtId: Long): RequestRepairListResponse
    @PATCH("/volunteers/repairs/{repairId}/proceed")
    suspend fun patchRepairInfo(
        @Query("date") date: String,
        @Path("repairId") repairId: Long
    ): DefaultResponse
}