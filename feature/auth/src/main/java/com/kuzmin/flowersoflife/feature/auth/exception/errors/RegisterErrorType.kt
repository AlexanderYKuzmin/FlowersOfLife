package com.kuzmin.flowersoflife.feature.auth.exception.errors

import com.kuzmin.flowersoflife.core.local.validators.ValidationErrorType

enum class RegisterErrorType(override val code: String) : ValidationErrorType {
    EMAIL_INVALID("email_invalid"),
    PASSWORD_WEAK("password_weak"),
    PASSWORD_MISMATCH("password_mismatch")
}
