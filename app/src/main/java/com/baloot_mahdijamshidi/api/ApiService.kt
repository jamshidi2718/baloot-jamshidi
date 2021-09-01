package com.baloot_mahdijamshidi.api

import com.baloot_mahdijamshidi.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

//    @Headers("Content-Type: application/json")
    @GET("v2/everything")
    suspend fun getArticle(@Query("apiKey") apiKey:String ,
                           @Query("q") subject:String ,
                           @Query("pageSize") pageSize:Int,
                           @Query("page") page:Int
    ):  Response<ArticleResponse>

}