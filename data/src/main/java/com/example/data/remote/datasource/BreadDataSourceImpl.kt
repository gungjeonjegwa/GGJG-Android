package com.example.data.remote.datasource

import com.example.data.remote.api.BreadAPI
import com.example.data.remote.response.BreadResponse
import com.example.data.utils.HttpHandler
import javax.inject.Inject

class BreadDataSourceImpl @Inject constructor(
    private val breadAPI: BreadAPI
) : BreadDataSource {
    override suspend fun allBread(page: String, size: String): BreadResponse =
        HttpHandler<BreadResponse>()
            .httpRequest { breadAPI.allBread(page, size) }
            .sendRequest()
}