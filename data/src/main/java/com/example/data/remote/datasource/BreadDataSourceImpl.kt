package com.example.data.remote.datasource

import com.example.data.remote.api.BreadAPI
import com.example.data.remote.model.BreadModel
import com.example.data.remote.response.bread.*
import com.example.data.utils.GGJGApiCall
import javax.inject.Inject

class BreadDataSourceImpl @Inject constructor(
    private val breadAPI: BreadAPI,
) : BreadDataSource {
    override suspend fun allBread(page: Int): BreadResponse = GGJGApiCall {
        breadAPI.allBread(page = page)
    }

    override suspend fun categoryBread(page: Int, category: String): BreadResponse =
        GGJGApiCall {
            breadAPI.categoryBread(page = page, category = category)
        }

    override suspend fun detailBread(id: String): DetailBreadResponse = GGJGApiCall {
        breadAPI.detailBread(id = id)
    }

    override suspend fun likeBread(id: String) = GGJGApiCall {
        breadAPI.likeBread(id = id)
    }

    override suspend fun allLikeBread(): List<BreadModel> = GGJGApiCall {
        breadAPI.allLikeBread()
    }

    override suspend fun banner(): List<BannerResponse> = GGJGApiCall {
        breadAPI.banner()
    }

    override suspend fun searchBread(title: String): List<SearchResponse> = GGJGApiCall {
        breadAPI.searchBread(title = title)
    }

    override suspend fun resultBread(title: String): List<BreadModel> = GGJGApiCall {
        breadAPI.resultBread(title = title)
    }
}