package com.kuzmin.flowersoflife.data_provider.repository

import android.util.Log
import com.kuzmin.flowersoflife.core.api.ApiService
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.data_provider.mapper.toUserDto
import com.kuzmin.flowersoflife.data_provider.mapper.toUserModel
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {
    override fun getUserById(userId: String): User? {
        // TODO: Implement actual logic
        return null
    }

    override fun getUserFamilyById(userId: String): UserFamily? {
        // TODO: Implement actual logic
        return null
    }

    override suspend fun saveUserFamily(userFamily: UserFamily): User? {
        val result = apiService.createDbUser(
            userFamily.toUserDto()
        )
        Log.d("Cab-15", "Save user result: ${result.body()}")
        return result.body()?.toUserModel()
    }
}