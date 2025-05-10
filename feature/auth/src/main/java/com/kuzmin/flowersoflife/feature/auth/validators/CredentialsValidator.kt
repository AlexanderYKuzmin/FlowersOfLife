package com.kuzmin.flowersoflife.feature.auth.validators

import com.kuzmin.flowersoflife.core.domain.extensions.isEmailConsistent
import com.kuzmin.flowersoflife.core.domain.extensions.isPasswordConsistent
import com.kuzmin.flowersoflife.core.local.validators.Validator
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType


object CredentialsValidator : Validator<AuthCredentials, RegisterErrorType> {
    override fun validate(data: AuthCredentials): Set<RegisterErrorType> = buildSet {

        if (data.email.isBlank()) add(RegisterErrorType.EMAIL_EMPTY)
        else if (!data.email.isEmailConsistent()) add(RegisterErrorType.EMAIL_INVALID)

        if (data.password.isBlank()) add(RegisterErrorType.PASSWORD_EMPTY)
        else if (!data.password.isPasswordConsistent()) add(RegisterErrorType.PASSWORD_WEAK)
    }
}