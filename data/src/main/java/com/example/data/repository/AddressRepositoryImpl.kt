package com.example.data.repository

import com.example.data.remote.datasource.AddressDataSource
import com.example.data.remote.response.address.toEntity
import com.example.domain.model.AddressModel
import com.example.domain.repository.AddressRepository
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressDataSource: AddressDataSource,
) : AddressRepository {
    override suspend fun getAddress(keyword: String): List<AddressModel> =
        addressDataSource.getAddress(keyword = keyword).results.juso.map { it.toEntity() }
}