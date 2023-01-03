package com.example.data.remote.datasource

import com.example.data.remote.api.BreadAPI
import com.example.data.remote.response.bread.*
import com.example.data.utils.HttpHandler
import javax.inject.Inject

class BreadDataSourceImpl @Inject constructor(
    private val breadAPI: BreadAPI,
) : BreadDataSource {
    override suspend fun allBread(page: String, size: String): BreadResponse =
        HttpHandler<BreadResponse>()
            .httpRequest { breadAPI.allBread(page, size) }
            .sendRequest()

    override suspend fun categoryBread(
        page: String,
        size: String,
        category: String,
    ): BreadResponse =
        HttpHandler<BreadResponse>()
            .httpRequest { breadAPI.categoryBread(page, size, category) }
            .sendRequest()

    override suspend fun detailBread(id: String): DetailBreadResponse =
        HttpHandler<DetailBreadResponse>()
            .httpRequest { breadAPI.detailBread(id) }
            .sendRequest()

    override suspend fun likeBread(id: String) =
        HttpHandler<Unit>()
            .httpRequest { breadAPI.likeBread(id) }
            .sendRequest()

    override suspend fun allLikeBread(): List<LikeBreadResponse> =
        HttpHandler<List<LikeBreadResponse>>()
            .httpRequest { breadAPI.allLikeBread() }
            .sendRequest()

    override suspend fun banner(): List<BannerResponse> =
        HttpHandler<List<BannerResponse>>()
            .httpRequest { breadAPI.banner() }
            .sendRequest()

    override suspend fun searchBread(title: String): List<SearchResponse> =
        HttpHandler<List<SearchResponse>>()
            .httpRequest { breadAPI.searchBread(title) }
            .sendRequest()

    override suspend fun resultBread(title: String): List<SearchResultResponse> =
        HttpHandler<List<SearchResultResponse>>()
            .httpRequest { breadAPI.resultBread(title) }
            .sendRequest()
}