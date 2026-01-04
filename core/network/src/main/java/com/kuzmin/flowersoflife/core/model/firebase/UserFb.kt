package com.kuzmin.flowersoflife.core.model.firebase

data class UserFb(
    val uid: String,
    val email: String,
    val password: String,
    val role: String,
    val name: String,
    val isAdmin: Boolean
)
