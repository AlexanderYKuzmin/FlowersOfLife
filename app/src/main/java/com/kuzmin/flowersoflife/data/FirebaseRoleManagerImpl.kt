package com.kuzmin.flowersoflife.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.kuzmin.flowersoflife.common.ext.awaitSingleValueEvent
import com.kuzmin.flowersoflife.core.domain.model.roles.RoleManager
import javax.inject.Inject

class FirebaseRoleManagerImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase
) : RoleManager {

    override suspend fun getUserRole(userId: String): String? {
        return try {
            val roleRef = database.getReference("users/$userId/role")
            val roleSnapshot = roleRef.awaitSingleValueEvent() // Расширение для Coroutine
            roleSnapshot.getValue(String::class.java)
        } catch (e: Exception) {
            null // Обработка ошибок (например, пользователя не существует)
        }
    }

    override suspend fun setUserRole(userId: String, role: String) {
        TODO("Not yet implemented")
    }
}