package com.kuzmin.flowersoflife.feature.api.usecases.user.local

import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily

interface GetUserFamilyFromLocalUseCase {
    suspend operator fun invoke(): UserFamily
}