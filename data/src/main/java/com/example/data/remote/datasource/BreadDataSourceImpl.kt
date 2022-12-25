package com.example.data.remote.datasource

import com.example.data.remote.api.BreadAPI
import com.example.data.remote.response.bread.BreadResponse
import com.example.data.remote.response.bread.DetailBreadResponse
import com.example.data.utils.HttpHandler
import javax.inject.Inject

class BreadDataSourceImpl @Inject constructor(
    private val breadAPI: BreadAPI
) : BreadDataSource {
    override suspend fun allBread(page: String, size: String): BreadResponse =
        HttpHandler<BreadResponse>()
            .httpRequest { breadAPI.allBread(page, size) }
            .sendRequest()

    override suspend fun categoryBread(
        page: String,
        size: String,
        category: String
    ): BreadResponse =
        HttpHandler<BreadResponse>()
            .httpRequest { breadAPI.categoryBread(page, size, category) }
            .sendRequest()

    override suspend fun detailBread(id: String): DetailBreadResponse =
        HttpHandler<DetailBreadResponse>()
            .httpRequest { breadAPI.detailBread(id) }
            .sendRequest()
}