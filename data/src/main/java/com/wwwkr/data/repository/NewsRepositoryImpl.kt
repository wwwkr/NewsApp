package com.wwwkr.data.repository

import com.wwwkr.data.datasource.local.NewsLocalSource
import com.wwwkr.data.datasource.remote.NewsRemoteSource
import com.wwwkr.data.extensions.toArticleModel
import com.wwwkr.data.extensions.toNewsEntity
import com.wwwkr.data.extensions.toNewsParam
import com.wwwkr.data.extensions.toNewsResponseModel
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.NewsParamModel
import com.wwwkr.domain.model.NewsResponseModel
import com.wwwkr.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteSource: NewsRemoteSource,
    private val newsLocalSource: NewsLocalSource
) : NewsRepository {

    override fun getNews(param: NewsParamModel): Flow<NewsResponseModel> = flow {
        newsRemoteSource.getNews(newsParam = param.toNewsParam()).collect {
            emit(it.toNewsResponseModel())
        }
    }

    override fun insertNews(item: ArticleModel): Flow<Unit> = flow {
        newsLocalSource.insertNews(item = item.toNewsEntity()).collect {
            emit(it)
        }
    }

    override fun selectNews(): Flow<List<ArticleModel>> = flow {
        newsLocalSource.selectNews().collect {
            emit(
                it.map { newsEntity ->
                    newsEntity.toArticleModel()
                }
            )
        }
    }

    override fun deleteNews(title: String): Flow<Unit> = flow {
        newsLocalSource.deleteNews(title = title).collect() {
            emit(it)
        }
    }

}