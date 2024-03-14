package com.wwwkr.data.extensions

import com.wwwkr.data.datasource.local.database.entity.NewsEntity
import com.wwwkr.data.model.request.NewsParam
import com.wwwkr.data.model.response.NewsResponse
import com.wwwkr.domain.model.ArticleModel
import com.wwwkr.domain.model.NewsParamModel
import com.wwwkr.domain.model.NewsResponseModel
import com.wwwkr.domain.model.SourceModel

fun NewsParamModel.toNewsParam(): NewsParam =
    NewsParam(
        country = country,
        apiKey = apiKey
    )

fun NewsResponse.toNewsResponseModel(): NewsResponseModel =
    NewsResponseModel(
        articles = articles?.map { ArticleModel(
            author = it.author,
            content = it.content,
            description = it.description,
            publishedAt = it.publishedAt,
            source = SourceModel(
                name = it.source?.name
            ),
            title = it.title,
            url = it.url,
            urlToImage = it.urlToImage,
            isScraped = false
        ) },
        status = status,
        totalResults = totalResults
    )

fun ArticleModel.toNewsEntity(): NewsEntity =
    NewsEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage,
        isScraped = isScraped
    )

fun NewsEntity.toArticleModel(): ArticleModel =
    ArticleModel(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = null,
        title = title,
        url = url,
        urlToImage = urlToImage,
        isScraped = isScraped
    )