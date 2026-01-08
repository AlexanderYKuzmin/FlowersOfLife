package com.kuzmin.flowersoflife.feature.api.usecases.user

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily

interface SaveUserFamilyToRemoteUseCase {
    suspend operator fun invoke(userFamily: UserFamily): User?
}