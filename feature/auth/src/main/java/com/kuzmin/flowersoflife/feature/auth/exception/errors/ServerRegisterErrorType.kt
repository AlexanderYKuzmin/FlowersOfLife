package com.kuzmin.flowersoflife.feature.auth.exception.errors

enum class ServerRegisterErrorType(val code: Int) {
    USER_ALREADY_EXISTS(1001),
    UNKNOWN_ERROR(9999);

    companion object {
        fun fromCode(code: Int): ServerRegisterErrorType {
            return values().find { it.code == code } ?: UNKNOWN_ERROR
        }
    }
}