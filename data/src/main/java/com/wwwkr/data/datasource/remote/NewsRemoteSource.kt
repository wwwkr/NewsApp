package com.wwwkr.data.datasource.remote

import com.wwwkr.data.datasource.remote.api.ApiService
import com.wwwkr.data.model.request.NewsParam
import com.wwwkr.data.model.response.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRemoteSource @Inject constructor(private val apiService: ApiService) {

    fun getNews(newsParam: NewsParam) : Flow<NewsResponse> = flow {
        emit(apiService.getNews(country = newsParam.country, apiKey = newsParam.apiKey))
    }
}