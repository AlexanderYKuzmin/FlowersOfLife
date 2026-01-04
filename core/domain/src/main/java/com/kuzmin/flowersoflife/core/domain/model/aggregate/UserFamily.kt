package com.kuzmin.flowersoflife.core.domain.model.aggregate

import com.kuzmin.flowersoflife.core.domain.model.Family
import com.kuzmin.flowersoflife.core.domain.model.User

data class UserFamily(
    val user: User,
    val family: Family,
)
