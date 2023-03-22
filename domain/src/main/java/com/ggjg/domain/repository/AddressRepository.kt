package com.ggjg.domain.repository

import com.ggjg.domain.model.AddressModel

interface AddressRepository {
    suspend fun getAddress(keyword: String): List<AddressModel>
}