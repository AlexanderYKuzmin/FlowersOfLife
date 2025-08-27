package com.kuzmin.flowersoflife.core.domain.usecases.children

import com.kuzmin.flowersoflife.core.domain.model.family_members.Child

interface GetChildrenListUseCase {
    suspend operator fun invoke(parentId: String): List<Child>
}