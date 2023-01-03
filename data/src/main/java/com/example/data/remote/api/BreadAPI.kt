package com.example.data.remote.api

import com.example.data.remote.response.bread.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BreadAPI {
    @GET("/bread")
    suspend fun allBread(
        @Query("page") page: String,
        @Query("size") size: String,
    ): BreadResponse

    @GET("/bread/kind")
    suspend fun categoryBread(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("category") category: String,
    ): BreadResponse

    @GET("/bread/{id}")
    suspend fun detailBread(
        @Path("id") id: String,
    ): DetailBreadResponse

    @POST("/bread/likeitem/{breadId}")
    suspend fun likeBread(
        @Path("breadId") id: String,
    )

    @GET("/bread/likeitem")
    suspend fun allLikeBread(): List<LikeBreadResponse>

    @GET("/bread/banner")
    suspend fun banner(): List<BannerResponse>

    @GET("/bread/search")
    suspend fun searchBread(
        @Query("title") title: String,
    ): List<SearchResponse>

    @GET("/bread/relationsearch")
    suspend fun resultBread(
        @Query("title") title: String,
    ): List<SearchResultResponse>
}