package com.ggjg.data.remote.datasource

import com.ggjg.data.remote.response.address.AddressResponse

interface AddressDataSource {
    suspend fun getAddress(keyword: String): AddressResponse
}