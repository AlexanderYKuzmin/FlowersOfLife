package com.kuzmin.flowersoflife.feature.auth.api.usecases

import com.kuzmin.flowersoflife.core.domain.model.User

interface GetUserFromFbUseCase {

    suspend operator fun invoke(): User
}