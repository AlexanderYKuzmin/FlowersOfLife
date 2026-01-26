package com.kuzmin.flowersoflife.feature.api.usecases.user.remote

import com.kuzmin.flowersoflife.core.domain.model.User

interface GetUserFromRemoteUseCase {
    suspend operator fun invoke(userId: String): User?
}