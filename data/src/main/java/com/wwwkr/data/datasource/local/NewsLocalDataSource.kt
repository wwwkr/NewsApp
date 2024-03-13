package com.wwwkr.data.datasource.local

import com.wwwkr.data.datasource.local.database.entity.NewsEntity
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface NewsLocalDataSource {

    val getScrapNewsStateFlow: MutableStateFlow<UiState<List<ArticleModel>>>

    suspend fun insertNews(item: NewsEntity): Flow<Unit>
    suspend fun selectNews(): Flow<List<NewsEntity>>
    suspend fun deleteNews(title: String): Flow<Unit>

}