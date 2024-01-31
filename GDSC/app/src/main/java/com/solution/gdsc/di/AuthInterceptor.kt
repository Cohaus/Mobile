package com.solution.gdsc.di

import android.util.Log
import com.solution.gdsc.ChallengeApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // AccessToken을 동기적으로 얻기
        val accessToken = runBlocking(Dispatchers.IO) {
            ChallengeApplication.getInstance().tokenManager.getAccessToken()
        }

        return if (accessToken.isNullOrEmpty()) {
            // 사용자 토큰이 없을 경우 그냥 요청 전송
            Log.e("OkHttp", "Request without access token: ${originalRequest.url}")
            chain.proceed(originalRequest)
        } else {
            // 새로운 헤더를 추가한 새로운 Request를 생성
            val authRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            // 새로운 Request로 다시 요청 보내기
            chain.proceed(authRequest)
        }
    }
}