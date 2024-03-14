package com.wwwkr.data.repository

import com.wwwkr.data.datasource.local.NewsLocalDataSource
import com.wwwkr.data.datasource.remote.NewsRemoteDataSource
import com.wwwkr.data.extensions.toArticleModel
import com.wwwkr.data.extensions.toNewsEntity
import com.wwwkr.data.extensions.toNewsParam
import com.wwwkr.data.extensions.toNewsResponseModel
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.NewsParamModel
import com.wwwkr.domain.model.NewsResponseModel
import com.wwwkr.domain.model.UiState
import com.wwwkr.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {

    override val getNewsStateFlow = newsRemoteDataSource.getNewsStateFlow
    override val getScrapNewsStateFlow = newsLocalDataSource.getScrapNewsStateFlow

    override fun getNews(param: NewsParamModel): Flow<NewsResponseModel> = flow {
        newsRemoteDataSource.getNews(newsParam = param.toNewsParam()).collect {
            emit(it.toNewsResponseModel())
        }
    }

    override fun insertNews(item: ArticleModel): Flow<Unit> = flow {
        newsLocalDataSource.insertNews(item = item.toNewsEntity()).collect {
            emit(it)
        }
    }

    override fun selectNews(): Flow<List<ArticleModel>> = flow {
        newsLocalDataSource.selectNews().collect {
            emit(
                it.map { newsEntity ->
                    newsEntity.toArticleModel()
                }
            )
        }
    }

    override fun deleteNews(title: String): Flow<Unit> = flow {
        newsLocalDataSource.deleteNews(title = title).collect() {
            emit(it)
        }
    }

}