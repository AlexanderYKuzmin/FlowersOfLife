package com.kuzmin.flowersoflife.core.domain.storage

import com.kuzmin.flowersoflife.core.domain.model.Family
import com.kuzmin.flowersoflife.core.domain.model.User

interface PrefManager {

    suspend fun getUser(): User

    suspend fun saveUser(user: User)

    suspend fun deleteUser()

    suspend fun getFamily(): Family

    suspend fun saveFamily(family: Family)

    suspend fun deleteFamily()
}