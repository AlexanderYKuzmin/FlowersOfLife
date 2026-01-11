package com.kuzmin.flowersoflife.feature.api.usecases.user.local

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily

interface SaveUserFamilyToLocalUseCase {
    suspend operator fun invoke(userFamily: UserFamily): User?
}