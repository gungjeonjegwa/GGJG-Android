package com.example.data.remote.datasource

import com.example.data.remote.api.AddressAPI
import com.example.data.remote.response.address.AddressResponse
import com.example.data.utils.GGJGApiCall
import javax.inject.Inject

class AddressDataSourceImpl @Inject constructor(
    private val addressAPI: AddressAPI,
) : AddressDataSource {
    override suspend fun getAddress(keyword: String): AddressResponse = GGJGApiCall {
        addressAPI.getAddress(keyword = keyword)
    }
}