package com.kuzmin.flowersoflife.data_provider.repository

import com.kuzmin.flowersoflife.core.api.ApiService
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.data_provider.mapper.toUserDto
import com.kuzmin.flowersoflife.data_provider.mapper.toUserFamily
import com.kuzmin.flowersoflife.data_provider.mapper.toUserModel
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {
    override fun getUserById(userId: String): User? {
        // TODO: Implement actual logic
        return null
    }

    override suspend fun getUserFamilyById(userId: String): UserFamily? {
        val result = apiService.getUserById(userId)
        return result.body()?.toUserFamily()
    }

    override suspend fun saveUserFamily(userFamily: UserFamily): User? {
        val result = apiService.createDbUser(
            userFamily.toUserDto()
        )
        return result.body()?.toUserModel()
    }
}