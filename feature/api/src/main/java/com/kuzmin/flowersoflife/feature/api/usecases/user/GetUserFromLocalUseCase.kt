package com.kuzmin.flowersoflife.feature.api.usecases.user

import com.kuzmin.flowersoflife.core.domain.model.User

interface GetUserFromLocalUseCase {

    suspend operator fun invoke(): User
}