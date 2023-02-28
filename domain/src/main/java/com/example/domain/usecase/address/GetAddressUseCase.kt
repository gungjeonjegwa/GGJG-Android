package com.example.domain.usecase.address

import com.example.domain.repository.AddressRepository
import javax.inject.Inject

class GetAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository,
) {
    suspend operator fun invoke(keyword: String) = kotlin.runCatching {
        addressRepository.getAddress(keyword = keyword)
    }
}