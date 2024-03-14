package com.wwwkr.domain.usecase

import com.wwwkr.domain.model.UiState
import com.wwwkr.domain.repository.NewsRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetScrapNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    private val _getScrapNewsStateFlow = newsRepository.getScrapNewsStateFlow
    val getScrapNewsStateFlow = _getScrapNewsStateFlow.asStateFlow()

    suspend operator fun invoke() {
        newsRepository.selectNews()
            .collectLatest {
                _getScrapNewsStateFlow.value = UiState.Success(it)
            }
    }
}