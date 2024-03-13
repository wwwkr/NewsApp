package com.wwwkr.data.datasource.remote

import com.wwwkr.data.model.request.NewsParam
import com.wwwkr.data.model.response.NewsResponse
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface NewsRemoteDataSource {

    val getNewsStateFlow: MutableStateFlow<UiState<List<ArticleModel>>>

    fun getNews(newsParam: NewsParam) : Flow<NewsResponse>

}