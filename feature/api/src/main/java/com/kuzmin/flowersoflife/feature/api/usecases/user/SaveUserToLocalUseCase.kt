package com.kuzmin.flowersoflife.feature.api.usecases.user

import com.kuzmin.flowersoflife.core.domain.model.User

interface SaveUserToLocalUseCase {
    suspend operator fun invoke(user: User)
}