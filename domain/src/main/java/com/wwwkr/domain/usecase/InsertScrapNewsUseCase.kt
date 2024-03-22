package com.wwwkr.domain.usecase

import android.util.Log
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.NewsParamModel
import com.wwwkr.domain.model.UiState
import com.wwwkr.domain.repository.NewsRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class InsertScrapNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    private val _getNewsStateFlow = newsRepository.getNewsStateFlow

    suspend operator fun invoke(item: ArticleModel) {
        newsRepository.insertNews(item = item)
            .collectLatest {
                val currentState = _getNewsStateFlow.value
                if (currentState is UiState.Success) {
                    val currentData = currentState.data
                    val updatedArticles = currentData.map { newsItem ->
                        if (newsItem.title == item.title) {
                            Log.e("TAG","CHECK TITLE : ${item.title} ${item.isScraped}")
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