package com.kuzmin.flowersoflife.core.domain.usecases.children

import com.kuzmin.flowersoflife.core.domain.model.family_members.ChildDetails

interface GetChildDetailsUseCase {
    suspend operator fun invoke(childId: String): ChildDetails
}