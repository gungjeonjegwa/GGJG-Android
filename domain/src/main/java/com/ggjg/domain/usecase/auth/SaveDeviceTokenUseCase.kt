package com.ggjg.domain.usecase.auth

import com.ggjg.domain.param.auth.DeviceTokenParam
import com.ggjg.domain.repository.AuthRepository
import javax.inject.Inject

class SaveDeviceTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(deviceTokenParam: DeviceTokenParam) = kotlin.runCatching {
        authRepository.saveDeviceToken(deviceTokenParam = deviceTokenParam)
    }
}