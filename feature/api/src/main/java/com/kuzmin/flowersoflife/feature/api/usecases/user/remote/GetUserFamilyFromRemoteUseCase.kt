package com.kuzmin.flowersoflife.feature.api.usecases.user.remote

import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily

interface GetUserFamilyFromRemoteUseCase {
    suspend operator fun invoke(userId: String): UserFamily?
}