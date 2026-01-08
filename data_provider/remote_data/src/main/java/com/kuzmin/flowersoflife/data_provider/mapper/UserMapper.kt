package com.kuzmin.flowersoflife.data_provider.mapper

import com.kuzmin.flowersoflife.core.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.core.model.dto.UserDto
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

fun UserFamily.toUserDto(): UserDto {
    return UserDto(
        userId = user.userId,
        email = user.email,
        role = user.role?.value ?: "parent",
        name = user.name,
        isAdmin = user.isAdmin,
        familyId = user.familyId,
        familyName = family.familyName,
        familyCode = family.familyCode,
        emailVerified = false
    )
}

fun UserDto.toUserModel(): User {
    return User(
        userId = userId,
        email = email,
        role = UserRole.fromValue(role),
        name = name,
        isAdmin = isAdmin,
        familyId = familyId,
        walletId = walletId
    )
}

