package com.wwwkr.domain.usecase

import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.NewsParamModel
import com.wwwkr.domain.model.NewsResponseModel
import com.wwwkr.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    fun getNews(country : String): Flow<NewsResponseModel> {
        return newsRepository.getNews(
            NewsParamModel(
                country = country,
                apiKey = "06b985d51c424ae8976546bc63c03cce"
            )
        )
    }

    fun insertNews(item: ArticleModel): Flow<Unit> {
        return newsRepository.insertNews(item = item)
    }

    fun selectNews(): Flow<List<ArticleModel>> {
        return newsRepository.selectNews()
    }

    fun deleteNews(title: String): Flow<Unit> = newsRepository.deleteNews(title = title)
}