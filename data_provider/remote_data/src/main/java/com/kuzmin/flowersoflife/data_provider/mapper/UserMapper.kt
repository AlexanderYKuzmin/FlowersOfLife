package com.kuzmin.flowersoflife.data_provider.mapper

import com.kuzmin.flowersoflife.core.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.model.firebase.AuthCredentialsFb
import com.kuzmin.flowersoflife.core.model.firebase.UserFb


fun AuthCredentials.toAuthCredentialsFb(): AuthCredentialsFb {
    return AuthCredentialsFb(
        email = email,
        password = password
    )
}

fun UserFb.toUserModel(): User {
    return User(
        userId = uid,
        email = email,
        password = password,
        role = UserRole.valueOf(role),
        name = name,
        isAdmin = isAdmin,
        familyId = ""
    )
}

