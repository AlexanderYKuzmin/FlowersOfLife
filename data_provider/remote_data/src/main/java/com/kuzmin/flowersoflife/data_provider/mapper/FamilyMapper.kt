package com.kuzmin.flowersoflife.data_provider.mapper

import com.kuzmin.flowersoflife.core.domain.model.Family
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.model.family_members.Child
import com.kuzmin.flowersoflife.core.model.dto.ChildDto

fun Child.toChildDto(family: Family): ChildDto {
    return ChildDto(
        role = UserRole.CHILD.value,
        name = name,
        familyId = family.familyId,
    )
}