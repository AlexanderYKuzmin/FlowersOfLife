package com.kuzmin.flowersoflife.feature.api.usecases.home

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child

interface CreateChildRemoteUseCase {
    suspend operator fun invoke(child: Child)
}