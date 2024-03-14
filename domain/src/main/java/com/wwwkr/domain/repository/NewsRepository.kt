package com.wwwkr.domain.repository

import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.NewsParamModel
import com.wwwkr.domain.model.NewsResponseModel
import com.wwwkr.domain.model.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface NewsRepository {

    val getScrapNewsStateFlow: MutableStateFlow<UiState<List<ArticleModel>>>
    val getNewsStateFlow: MutableStateFlow<UiState<List<ArticleModel>>>

    fun getNews(param: NewsParamModel): Flow<NewsResponseModel>

    fun insertNews(item: ArticleModel): Flow<Unit>

    fun selectNews(): Flow<List<ArticleModel>>

    fun deleteNews(title: String): Flow<Unit>
}