package com.wwwkr.domain.model

data class NewsResponseModel(
    val articles: List<ArticleModel>?,
    val status: String?,
    val totalResults: Int?
)

data class ArticleModel(
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    val source: SourceModel? = null,
    val title: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    var isScraped: Boolean = false,
    val memo: String = ""
)

data class SourceModel(
    val name: String?
)

