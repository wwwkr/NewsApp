package com.wwwkr.domain.usecase

import com.wwwkr.domain.model.NewsParamModel
import com.wwwkr.domain.model.UiState
import com.wwwkr.domain.repository.NewsRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    private val _getNewsStateFlow = newsRepository.getNewsStateFlow
    val getNewsStateFlow = _getNewsStateFlow.asStateFlow()

    @OptIn(FlowPreview::class)
    suspend operator fun invoke(country: String){
        newsRepository.getNews(
            NewsParamModel(
                country = country,
                apiKey = "06b985d51c424ae8976546bc63c03cce"
            )
        ).flatMapConcat { news ->
            newsRepository.selectNews().map { savedNews ->
                // news.articles 리스트와 savedNews 리스트의 content를 비교하여 isScraped를 true로 설정합니다.
                news.articles?.forEach { article ->
                    savedNews.forEach { savedArticle ->
                        if (article.title == savedArticle.title) {
                            article.isScraped = true
                        }
                    }
                }
                // 변경된 news를 반환합니다.
                news
            }
        }
            .onStart {
                _getNewsStateFlow.value = UiState.Loading
            }.catch {
                _getNewsStateFlow.value = UiState.Error(it.message.toString())
            }.collect {
                _getNewsStateFlow.value = UiState.Success(it.articles ?: listOf())
            }
    }

}