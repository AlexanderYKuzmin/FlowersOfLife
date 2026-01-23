package com.kuzmin.flowersoflife.feature.api.usecases.user.remote

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily

interface SaveUserRemoteUseCase {
    suspend operator fun invoke(userFamily: UserFamily): User?
}