package com.kuzmin.flowersoflife.feature.auth.api.usecases

import com.kuzmin.flowersoflife.core.domain.model.User

interface SaveUserDatastoreUseCase {
    suspend operator fun invoke(user: User)
}