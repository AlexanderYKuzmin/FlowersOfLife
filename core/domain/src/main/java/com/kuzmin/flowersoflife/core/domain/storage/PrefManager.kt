package com.kuzmin.flowersoflife.core.domain.storage

interface PrefManager {

    suspend fun getUser(): com.kuzmin.flowersoflife.core.domain.model.User

    suspend fun saveUser(user: com.kuzmin.flowersoflife.core.domain.model.User)

    suspend fun deleteUser()
}