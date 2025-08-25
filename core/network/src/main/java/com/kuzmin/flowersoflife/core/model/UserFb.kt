package com.kuzmin.flowersoflife.core.model

data class UserFb(
    val uid: String? = null,
    val firstName: String,
    val email: String,
    val role: String,
    val password: String,
    val groupName: String,
    val isAdmin: Boolean
)
