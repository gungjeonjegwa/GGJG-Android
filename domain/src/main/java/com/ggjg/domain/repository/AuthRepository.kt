package com.ggjg.domain.repository

import com.ggjg.domain.entity.auth.CheckEntity
import com.ggjg.domain.entity.auth.LoginEntity
import com.ggjg.domain.entity.auth.ProfileEntity
import com.ggjg.domain.entity.auth.ProfilePrivateEntity
import com.ggjg.domain.model.AddressModel
import com.ggjg.domain.param.auth.LoginParam
import com.ggjg.domain.param.auth.SignUpParam

interface AuthRepository {
    suspend fun signUp(signUpParam: SignUpParam)
    suspend fun login(loginParam: LoginParam): LoginEntity
    suspend fun logout()
    suspend fun idCheck(id: String): CheckEntity
    suspend fun emailCheck(email: String): CheckEntity
    suspend fun changeAddress(address: AddressModel)
    suspend fun recentAddress(): List<AddressModel>
    suspend fun newAddress(address: AddressModel)
    suspend fun profile(): ProfileEntity
    suspend fun profilePrivate(): ProfilePrivateEntity
    suspend fun giftStamp()
    suspend fun saveToken(access: String?, refresh: String?, expiredAt: String?)
    suspend fun isLogin(): Boolean
}