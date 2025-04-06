package com.kuzmin.flowersoflife.core.domain.model

enum class UserRole(val value: String) {
    PARENT ("parent"),
    CHILD ("child");

    fun getRole(): String = value

    fun getRoleRu(): String = when (this) {
        PARENT -> "Родитель"
        CHILD -> "Ребенок"
    }
}