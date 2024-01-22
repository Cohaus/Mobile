package com.solution.gdsc.di

import com.solution.gdsc.data.remote.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideLoginService(@NetworkModule.LoginInterceptorOkHttpClient retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)
}