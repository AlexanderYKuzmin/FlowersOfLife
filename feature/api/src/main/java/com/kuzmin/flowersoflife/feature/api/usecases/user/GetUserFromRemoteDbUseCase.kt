package com.kuzmin.flowersoflife.feature.api.usecases.user

import com.kuzmin.flowersoflife.core.domain.model.User

interface GetUserFromRemoteDbUseCase {
    suspend operator fun invoke(userId: String): User?
}