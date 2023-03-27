package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.model.BreadModel
import com.ggjg.data.remote.response.bread.BannerResponse
import com.ggjg.data.remote.response.bread.BreadResponse
import com.ggjg.data.remote.response.bread.DetailBreadResponse
import com.ggjg.data.remote.response.bread.SearchResponse
import com.ggjg.data.remote.api.BreadAPI
import handler.GGJGApiCall
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