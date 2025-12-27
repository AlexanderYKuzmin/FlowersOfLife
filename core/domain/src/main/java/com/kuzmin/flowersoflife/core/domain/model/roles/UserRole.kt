package com.kuzmin.flowersoflife.core.domain.model.roles

enum class UserRole(val value: String) {
    PARENT("parent"),
    CHILD("child");

    companion object {
        fun fromValue(value: String): UserRole? = entries.find { it.value == value }
    }
}