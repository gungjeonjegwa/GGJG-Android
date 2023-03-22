package com.ggjg.domain.usecase.auth

import com.ggjg.domain.model.AddressModel
import com.ggjg.domain.repository.AuthRepository
import javax.inject.Inject

class NewAddressUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(address: AddressModel) = kotlin.runCatching {
        authRepository.newAddress(address)
    }
}