package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.api.AddressAPI
import com.ggjg.data.remote.response.address.AddressResponse
import handler.GGJGApiCall
import javax.inject.Inject

class AddressDataSourceImpl @Inject constructor(
    private val addressAPI: AddressAPI,
) : AddressDataSource {
    override suspend fun getAddress(keyword: String): AddressResponse = GGJGApiCall {
        addressAPI.getAddress(keyword = keyword)
    }
}