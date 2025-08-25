package com.kuzmin.flowersoflife.model

data class MockUserFb(
    val uid: String? = null,
    val firstName: String = "Test",
    val email: String = "test@example.com",
    val role: String = "PARENT",
    val password: String = "123456",
    val groupName: String = "G1",
    val isAdmin: Boolean = true
)
