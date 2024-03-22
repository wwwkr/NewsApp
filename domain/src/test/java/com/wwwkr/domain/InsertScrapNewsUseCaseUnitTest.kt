package com.wwwkr.domain

import com.wwwkr.domain.extensions.toDataList
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.UiState
import com.wwwkr.domain.repository.NewsRepository
import com.wwwkr.domain.usecase.InsertScrapNewsUseCase
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class InsertScrapNewsUseCaseUnitTest : ShouldSpec({

    val newsRepository = mockk<NewsRepository>()

    val initialArticle = ArticleModel(title = "스크랩 뉴스", isScraped = false)

    coEvery { newsRepository.insertNews(initialArticle) } returns flowOf(Unit)
    coEvery { newsRepository.getNewsStateFlow } returns MutableStateFlow(UiState.Success(listOf(initialArticle)))

    val insertScrapNewsUseCase = InsertScrapNewsUseCase(newsRepository)

    runBlocking {

        should("뉴스를 스크랩 한다") {

            // Given: 초기 ArticleModel의 스크랩 상태가 false 확인
            newsRepository.getNewsStateFlow.toDataList().first().isScraped shouldBe false

            // When: InsertScrapNewsUseCase를 호출하여 스크랩
            insertScrapNewsUseCase(initialArticle)

            // Then: 뉴스가 스크랩 됐는지 확인
            newsRepository.getNewsStateFlow.toDataList().first().isScraped shouldBe true

        }

    }

})