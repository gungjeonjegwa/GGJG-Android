package com.example.data.remote.api

import com.example.data.remote.response.bread.BreadResponse
import com.example.data.remote.response.bread.DetailBreadResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreadAPI {
    @GET("/bread")
    suspend fun allBread(
        @Query("page") page: String,
        @Query("size") size: String
    ): BreadResponse

    @GET("/bread/kind")
    suspend fun categoryBread(
        @Query("page") page: String,
        @Query("size") size: String,
        @Query("category") category: String
    ): BreadResponse

    @GET("/bread/{id}")
    suspend fun detailBread(
        @Path("id") id: String
    ): DetailBreadResponse
}