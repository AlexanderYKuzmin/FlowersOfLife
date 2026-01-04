package com.kuzmin.flowersoflife.data_provider.repository

import com.kuzmin.flowersoflife.core.api.ApiService
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
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
}