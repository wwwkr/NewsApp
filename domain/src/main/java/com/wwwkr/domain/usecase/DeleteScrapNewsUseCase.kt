package com.wwwkr.domain.usecase

import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.UiState
import com.wwwkr.domain.repository.NewsRepository
import javax.inject.Inject

class DeleteScrapNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    private val _getNewsStateFlow = newsRepository.getNewsStateFlow
    private val _getScrapNewsStateFlow = newsRepository.getScrapNewsStateFlow

    suspend operator fun invoke(
        item: ArticleModel,
        isScrapView: Boolean
    ) {
        newsRepository.deleteNews(title = item.title.toString())
            .collect {

                if(isScrapView) {
                    val currentState = _getScrapNewsStateFlow.value
                    if (currentState is UiState.Success) {
                        val currentData = currentState.data
                        val updatedArticles = currentData.filter { newsItem ->
                            newsItem.title != item.title
                        }

                        _getScrapNewsStateFlow.value = UiState.Success(updatedArticles)
                    }
                }else {
                    val currentState = _getNewsStateFlow.value
                    if (currentState is UiState.Success) {
                        val currentData = currentState.data
                        val updatedArticles = currentData.map { newsItem ->
                            if (newsItem.title == item.title) {
                                // 변경된 아이템과 동일한 아이템을 찾아서 스크랩 상태를 변경합니다.
                                item.copy(isScraped = !item.isScraped)
                            } else {
                                newsItem
                            }
                        }
                        // 변경된 데이터로 UiState를 갱신합니다.
                        _getNewsStateFlow.value = UiState.Success(updatedArticles)
                    }
                }
            }
    }

}