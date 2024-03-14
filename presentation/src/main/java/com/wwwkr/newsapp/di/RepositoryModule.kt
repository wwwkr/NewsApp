package com.wwwkr.newsapp.di

import com.wwwkr.data.datasource.local.NewsLocalDataSource
import com.wwwkr.data.datasource.local.NewsLocalDataSourceImpl
import com.wwwkr.data.datasource.remote.NewsRemoteDataSource
import com.wwwkr.data.repository.NewsRepositoryImpl
import com.wwwkr.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun newsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        localNewsDataSource: NewsLocalDataSource
    ): NewsRepository = NewsRepositoryImpl(newsRemoteDataSource = newsRemoteDataSource, newsLocalDataSource = localNewsDataSource)



}