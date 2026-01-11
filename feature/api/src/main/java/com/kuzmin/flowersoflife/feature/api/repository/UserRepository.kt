package com.kuzmin.flowersoflife.feature.api.repository

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily

interface UserRepository {
    fun getUserById(userId: String): User?

    suspend fun getUserFamilyById(userId: String): UserFamily?

    suspend fun saveUserFamily(userFamily: UserFamily): User?
}