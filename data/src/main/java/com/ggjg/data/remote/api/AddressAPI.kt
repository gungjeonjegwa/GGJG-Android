package com.ggjg.data.remote.api

import com.ggjg.data.BuildConfig
import com.ggjg.data.remote.response.address.AddressResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface AddressAPI {
    @GET
    suspend fun getAddress(
        @Url url: String = "https://business.juso.go.kr/addrlink/addrLinkApi.do",
        @Query("confmKey") confmKey: String = BuildConfig.JUSO_KEY,
        @Query("resultType") resultType: String = "json",
        @Query("countPerPage") countPerPage: Int = 100,
        @Query("keyword") keyword: String,
    ): AddressResponse
}