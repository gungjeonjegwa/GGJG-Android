package com.example.domain.repository

import com.example.domain.model.AddressModel

interface AddressRepository {
    suspend fun getAddress(keyword: String): List<AddressModel>
}