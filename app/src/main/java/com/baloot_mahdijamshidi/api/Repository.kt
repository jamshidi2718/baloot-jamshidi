package com.baloot_mahdijamshidi.api

import retrofit2.http.Query

class Repository(private val apiService: ApiService) {

    suspend fun getArticle(
        apiKey: String,
        subject: String,
        pageSize: Int,
        page: Int
    ) = apiService.getArticle(
        apiKey,
        subject,
        pageSize,
        page
    )

}