package com.kuzmin.flowersoflife.feature.auth.validators

import com.kuzmin.flowersoflife.core.domain.extensions.isEmailConsistent
import com.kuzmin.flowersoflife.core.domain.extensions.isPasswordConsistent
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.local.validators.ValidationErrorType
import com.kuzmin.flowersoflife.core.local.validators.Validator
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType

object RegisterUserValidator : Validator<User> {

    override fun validate(value: User): Map<ValidationErrorType, String> {
        val errors = mutableMapOf<ValidationErrorType, String>()

        if (!value.email.isEmailConsistent()) {
            errors[RegisterErrorType.EMAIL_INVALID] = "Неверный email"
        }

        if (!value.password.isPasswordConsistent()) {
            errors[RegisterErrorType.PASSWORD_WEAK] = "Пароль слишком простой"
        }

        return errors
    }
}