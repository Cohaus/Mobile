package com.solution.gdsc.di.module

import com.solution.gdsc.data.source.home.HomeDataSource
import com.solution.gdsc.data.source.home.HomeRepositoryImpl
import com.solution.gdsc.data.source.login.LoginDataSource
import com.solution.gdsc.data.source.login.LoginRepositoryImpl
import com.solution.gdsc.data.source.map.MapDataSource
import com.solution.gdsc.data.source.map.MapRepositoryImpl
import com.solution.gdsc.data.source.profile.UserMyPageDatasource
import com.solution.gdsc.data.source.profile.UserMyPageRepositoryImpl
import com.solution.gdsc.domain.repository.HomeRepository
import com.solution.gdsc.domain.repository.LoginRepository
import com.solution.gdsc.domain.repository.MapRepository
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

    @Singleton
    @Provides
    fun provideHomeRepository(homeDataSource: HomeDataSource): HomeRepository =
        HomeRepositoryImpl(homeDataSource)

    @Singleton
    @Provides
    fun provideMapRepository(mapDataSource: MapDataSource): MapRepository =
        MapRepositoryImpl(mapDataSource)
}