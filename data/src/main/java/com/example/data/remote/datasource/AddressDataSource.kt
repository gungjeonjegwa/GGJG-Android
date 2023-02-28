package com.example.data.remote.datasource

import com.example.data.remote.response.address.AddressResponse

interface AddressDataSource {
    suspend fun getAddress(keyword: String): AddressResponse
}