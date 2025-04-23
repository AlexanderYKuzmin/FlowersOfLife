package com.kuzmin.flowersoflife.feature.auth.exception

import com.kuzmin.flowersoflife.core.local.validators.ValidationErrorType
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType

class AuthRegisterException(
    private val errors: Map<ValidationErrorType, String>
) : Exception() {

    override val message: String
        get() = errors.values.firstOrNull() ?: "Ошибка регистрации"

    fun hasError(field: RegisterErrorType): Boolean = errors.containsKey(field)

    fun errorFor(field: RegisterErrorType): String? = errors[field]
}

