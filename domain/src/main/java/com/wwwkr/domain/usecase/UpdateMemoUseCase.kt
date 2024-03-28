package com.wwwkr.domain.usecase

import android.util.Log
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.UiState
import com.wwwkr.domain.repository.NewsRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class UpdateMemoUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    private val _getScrapNewsStateFlow = newsRepository.getScrapNewsStateFlow

    @OptIn(FlowPreview::class)
    suspend operator fun invoke(title: String, memo: String) {
        newsRepository.updateNews(title = title, memo = memo)
            .flatMapConcat { newsRepository.selectNews() }
            .collectLatest {
                _getScrapNewsStateFlow.value = UiState.Success(it)
            }
    }
}