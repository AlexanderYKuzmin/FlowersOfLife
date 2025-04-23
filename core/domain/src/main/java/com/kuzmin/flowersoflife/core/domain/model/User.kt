package com.kuzmin.flowersoflife.core.domain.model

data class User(
    val firstName: String = "",
    val groupName: String = "",
    val email: String = "",
    val role: UserRole? = null,
    val password: String = "",
    val isAdmin: Boolean = false,
    val uid: String? = null
) {
    val isUserConsistent: Boolean
        get() =
            firstName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
            role != null
}
