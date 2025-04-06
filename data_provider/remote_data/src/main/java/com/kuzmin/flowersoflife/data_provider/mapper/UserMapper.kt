package com.kuzmin.flowersoflife.data_provider.mapper

import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.model.UserFb
import javax.inject.Inject

class UserMapper @Inject constructor() {
    fun mapUserFbToUser(userFb: UserFb): User {
        return User(
            uid = userFb.uid,
            email = userFb.email,
            password = userFb.password,
            role = UserRole.valueOf(userFb.role),
            firstName = userFb.firstName
        )
    }

    fun mapUserToUserFb(user: User): UserFb {
        return UserFb(
            uid = user.uid,
            email = user.email,
            password = user.password,
            role = user.role!!.name,
            firstName = user.firstName
        )
    }
}