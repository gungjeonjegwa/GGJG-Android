package com.example.domain.usecase.auth

import com.example.domain.model.AddressModel
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class ChangeAddressUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(address: AddressModel) = kotlin.runCatching {
        authRepository.changeAddress(address)
    }
}

