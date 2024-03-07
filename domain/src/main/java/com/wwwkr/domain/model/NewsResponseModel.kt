package com.wwwkr.domain.model

data class NewsResponseModel(
    val articles: List<ArticleModel>?,
    val status: String?,
    val totalResults: Int?
)

data class ArticleModel(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceModel?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    var isScraped: Boolean
)

data class SourceModel(
    val name: String?
)

