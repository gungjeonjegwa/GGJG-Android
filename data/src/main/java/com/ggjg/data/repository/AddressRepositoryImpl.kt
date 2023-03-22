package com.ggjg.data.repository

import com.ggjg.data.remote.datasource.AddressDataSource
import com.ggjg.data.remote.response.address.toEntity
import com.ggjg.domain.model.AddressModel
import com.ggjg.domain.repository.AddressRepository
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressDataSource: AddressDataSource,
) : AddressRepository {
    override suspend fun getAddress(keyword: String): List<AddressModel> =
        addressDataSource.getAddress(keyword = keyword).results.juso.map { it.toEntity() }
}