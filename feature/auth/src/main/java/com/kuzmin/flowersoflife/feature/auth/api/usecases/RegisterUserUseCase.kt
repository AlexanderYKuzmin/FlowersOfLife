package com.kuzmin.flowersoflife.feature.auth.api.usecases

import com.kuzmin.flowersoflife.core.domain.model.User

interface RegisterUserUseCase {

    suspend operator fun invoke(user: User): User?
}