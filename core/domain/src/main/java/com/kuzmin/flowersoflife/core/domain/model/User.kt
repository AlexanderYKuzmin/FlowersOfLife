package com.kuzmin.flowersoflife.core.domain.model

data class User(
    val userId: String,
    val name: String = "",
    val email: String = "",
    val emailVerified: Boolean = false,
    val role: UserRole? = null,
    val isAdmin: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val familyId: String,
    val password: String = "",
    val avatarUrl: String? = null
) {
    val isUserConsistent: Boolean
        get() =
            name.isNotEmpty() && email.isNotEmpty() &&
            role != null

    val isUserRegistered: Boolean
        get() = userId.length >= 5

    companion object{
        fun getEmptyUser(): User = User("", "", "", false, null, false, 0, 0, "")
    }
}
