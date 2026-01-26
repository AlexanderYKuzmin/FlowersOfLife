package com.kuzmin.flowersoflife.feature.auth.domain.usecases.remote

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.feature.api.repository.UserRepository
import com.kuzmin.flowersoflife.feature.api.usecases.user.remote.UpdateUserRemoteUseCase

class UpdateUserRemoteUseCaseImpl(
    private val repository: UserRepository
) : UpdateUserRemoteUseCase {
    override suspend operator fun invoke(user: User) {
        //repository.updateUser(user) TODO
    }
}