package com.kuzmin.flowersoflife.core.domain.mapper

import com.kuzmin.flowersoflife.core.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.core.domain.model.User

fun User.toAuthCredentials(): AuthCredentials {
    return AuthCredentials(
        email = email,
        password = password
    )
}