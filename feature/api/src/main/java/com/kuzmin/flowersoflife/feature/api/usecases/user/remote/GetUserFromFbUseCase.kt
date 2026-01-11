package com.kuzmin.flowersoflife.feature.api.usecases.user.remote

import com.kuzmin.flowersoflife.core.domain.model.User

interface GetUserFromFbUseCase {

    suspend operator fun invoke(): User?
}