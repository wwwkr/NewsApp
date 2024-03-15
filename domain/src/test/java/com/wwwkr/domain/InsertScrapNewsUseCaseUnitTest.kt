package com.wwwkr.domain

import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.repository.NewsRepository
import com.wwwkr.domain.usecase.InsertScrapNewsUseCase
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class InsertScrapNewsUseCaseUnitTest: ShouldSpec() {
    init {
        val newsRepository = mockk<NewsRepository>()
        val insertScrapNewsUseCase = InsertScrapNewsUseCase(newsRepository = newsRepository)

        coEvery {
            insertScrapNewsUseCase(
                item = ArticleModel()
            )
        } returns Unit

        context("InsertScrapNewsUseCase") {

        }

        CoroutineScope(Dispatchers.IO).launch {
            val resultFlow = insertScrapNewsUseCase(
                item = ArticleModel()
            )

            resultFlow shouldBe Unit
        }

    }
}