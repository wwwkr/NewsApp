package com.wwwkr.newsapp.di

import com.wwwkr.data.datasource.local.NewsLocalDataSource
import com.wwwkr.data.datasource.local.NewsLocalDataSourceImpl
import com.wwwkr.data.datasource.local.database.AppDatabase
import com.wwwkr.data.datasource.remote.NewsRemoteDataSource
import com.wwwkr.data.datasource.remote.NewsRemoteDataSourceImpl
import com.wwwkr.data.datasource.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun newsRemoteDataSource(
        apiService: ApiService
    ): NewsRemoteDataSource = NewsRemoteDataSourceImpl(apiService = apiService)

    @Provides
    @Singleton
    fun newsLocalDataSource(
        database: AppDatabase
    ): NewsLocalDataSource = NewsLocalDataSourceImpl(database = database)


}