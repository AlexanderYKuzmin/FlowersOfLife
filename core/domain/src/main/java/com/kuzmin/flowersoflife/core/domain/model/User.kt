package com.kuzmin.flowersoflife.core.domain.model

data class User(
    val firstName: String = "",
    val email: String = "",
    val role: UserRole? = null,
    val password: String = "",
    val uid: String? = null
) {
    val isUserConsistent: Boolean
        get() =
            firstName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() &&
            role != null
}
