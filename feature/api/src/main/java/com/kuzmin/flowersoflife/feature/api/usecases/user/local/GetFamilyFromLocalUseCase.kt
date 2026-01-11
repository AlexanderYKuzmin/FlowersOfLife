package com.kuzmin.flowersoflife.feature.api.usecases.user.local

import com.kuzmin.flowersoflife.core.domain.model.Family

interface GetFamilyFromLocalUseCase {
    suspend operator fun invoke(): Family
}