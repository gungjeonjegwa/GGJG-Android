package com.ggjg.data.remote.api

import com.ggjg.data.remote.model.BreadModel
import com.ggjg.data.remote.response.bread.BannerResponse
import com.ggjg.data.remote.response.bread.BreadResponse
import com.ggjg.data.remote.response.bread.DetailBreadResponse
import com.ggjg.data.remote.response.bread.SearchResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BreadAPI {
    @GET("/bread")
    suspend fun allBread(
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ): BreadResponse

    @GET("/bread/kind")
    suspend fun categoryBread(
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
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
    suspend fun allLikeBread(): List<BreadModel>

    @GET("/bread/banner")
    suspend fun banner(): List<BannerResponse>

    @GET("/bread/search")
    suspend fun searchBread(
        @Query("title") title: String,
    ): List<SearchResponse>

    @GET("/bread/relationsearch")
    suspend fun resultBread(
        @Query("title") title: String,
    ): List<BreadModel>
}