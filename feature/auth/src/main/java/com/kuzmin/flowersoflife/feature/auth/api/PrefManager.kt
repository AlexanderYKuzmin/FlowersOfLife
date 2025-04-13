package com.kuzmin.flowersoflife.feature.auth.api

import com.kuzmin.flowersoflife.core.domain.model.User

interface PrefManager {

    suspend fun getUser(): com.kuzmin.flowersoflife.core.domain.model.User

    suspend fun saveUser(user: com.kuzmin.flowersoflife.core.domain.model.User)

    suspend fun deleteUser()
}