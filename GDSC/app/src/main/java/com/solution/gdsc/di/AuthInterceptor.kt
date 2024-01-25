package com.solution.gdsc.di

import com.solution.gdsc.ChallengeApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val tokenManager = ChallengeApplication.getInstance().tokenManager

        // 사용자 토큰이 없으면 그냥 요청을 전송
        if (!tokenManager.checkUserToken()) {
            return chain.proceed(originalRequest)
        }

        // AccessToken을 비동기적으로 얻기
        val accessTokenJob = CoroutineScope(Dispatchers.IO).async {
            try {
                tokenManager.getAccessToken()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        // 비동기적으로 AccessToken을 얻은 후 실행
        CoroutineScope(Dispatchers.Main).launch {
            val accessToken = accessTokenJob.await()

            // 새로운 헤더를 추가한 새로운 Request를 생성
            val authRequest = originalRequest.newBuilder()
                .addHeader("Authorization", accessToken ?: "")
                .build()

            // 새로운 Request로 다시 요청 보내기
            chain.proceed(authRequest)
        }

        // 원래의 Response를 반환
        return chain.proceed(originalRequest)
    }
}