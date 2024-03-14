package com.wwwkr.data.datasource.remote

import com.wwwkr.data.datasource.remote.api.ApiService
import com.wwwkr.data.model.request.NewsParam
import com.wwwkr.data.model.response.NewsResponse
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) : NewsRemoteDataSource{

    override val getNewsStateFlow = MutableStateFlow<UiState<List<ArticleModel>>>(UiState.Empty)
    override fun getNews(newsParam: NewsParam) : Flow<NewsResponse> = flow {
        emit(apiService.getNews(country = newsParam.country, apiKey = newsParam.apiKey))
    }
}