package com.wwwkr.domain.repository

import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.NewsParamModel
import com.wwwkr.domain.model.NewsResponseModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(param: NewsParamModel): Flow<NewsResponseModel>

    fun insertNews(item: ArticleModel): Flow<Unit>

    fun selectNews(): Flow<List<ArticleModel>>

    fun deleteNews(title: String): Flow<Unit>
}