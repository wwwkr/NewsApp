package com.wwwkr.data.datasource.remote.api

import com.wwwkr.data.model.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String
    ) : NewsResponse

}