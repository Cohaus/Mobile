package com.solution.gdsc.di.module

import com.solution.gdsc.di.AuthInterceptor
import com.solution.gdsc.util.Utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LoginInterceptorOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class CoHousInterceptorOkHttpClient

    @LoginInterceptorOkHttpClient
    @Singleton
    @Provides
    fun provideLoginOkHttpClient(loginInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loginInterceptor)
            .build()
    }

    @CoHousInterceptorOkHttpClient
    @Singleton
    @Provides
    fun provideCoHousOkHttpClient(
        otherInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(otherInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @LoginInterceptorOkHttpClient
    @Singleton
    @Provides
    fun provideLoginRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        @LoginInterceptorOkHttpClient client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @CoHousInterceptorOkHttpClient
    @Singleton
    @Provides
    fun provideCoHousRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        @CoHousInterceptorOkHttpClient client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()
}