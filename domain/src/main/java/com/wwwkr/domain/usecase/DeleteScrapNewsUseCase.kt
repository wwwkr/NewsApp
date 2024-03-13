package com.wwwkr.domain.usecase

import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.UiState
import com.wwwkr.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteScrapNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    private val _getScrapNewsStateFlow = newsRepository.getScrapNewsStateFlow

    suspend operator fun invoke(item: ArticleModel) {
        newsRepository.deleteNews(title = item.title.toString())
            .collect {
                val currentState = _getScrapNewsStateFlow.value
                if (currentState is UiState.Success) {
                    val currentData = currentState.data
                    val updatedArticles = currentData.filter { newsItem ->
                        newsItem.title != item.title
                    }

                    _getScrapNewsStateFlow.value = UiState.Success(updatedArticles)
                }
            }
    }

}