package com.solution.gdsc.di.module

import com.solution.gdsc.data.source.LoginDataSource
import com.solution.gdsc.data.source.LoginRepositoryImpl
import com.solution.gdsc.data.source.UserMyPageDatasource
import com.solution.gdsc.data.source.UserMyPageRepositoryImpl
import com.solution.gdsc.domain.repository.LoginRepository
import com.solution.gdsc.domain.repository.UserMyPageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLoginRepository(loginDataSource: LoginDataSource): LoginRepository =
        LoginRepositoryImpl(loginDataSource)

    @Singleton
    @Provides
    fun provideUserMyPageRepository(userMyPageDatasource: UserMyPageDatasource): UserMyPageRepository =
        UserMyPageRepositoryImpl(userMyPageDatasource)
}