package com.kuzmin.flowersoflife.core.domain.model.roles

interface RoleManager {
    suspend fun getUserRole(userId: String): String?
    suspend fun setUserRole(userId: String, role: String)
}