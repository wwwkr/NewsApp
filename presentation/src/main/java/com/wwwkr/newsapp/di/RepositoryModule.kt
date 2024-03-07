package com.wwwkr.newsapp.di

import com.wwwkr.data.datasource.local.NewsLocalSource
import com.wwwkr.data.datasource.remote.NewsRemoteSource
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
        newsRemoteSource: NewsRemoteSource,
        newsLocalSource: NewsLocalSource
    ): NewsRepository = NewsRepositoryImpl(newsRemoteSource = newsRemoteSource, newsLocalSource = newsLocalSource)



}