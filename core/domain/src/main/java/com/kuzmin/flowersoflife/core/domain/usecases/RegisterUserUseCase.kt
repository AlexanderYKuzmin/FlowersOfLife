package com.kuzmin.flowersoflife.core.domain.usecases

import com.kuzmin.flowersoflife.core.domain.model.User

interface RegisterUserUseCase {

    suspend operator fun invoke(user: User): User?
}