package com.kuzmin.flowersoflife.core.domain.usecases.auth

import com.kuzmin.flowersoflife.core.domain.model.User

interface GetUserFromLocalStorageUseCase {

    suspend operator fun invoke(): User
}