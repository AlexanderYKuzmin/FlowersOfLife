package com.kuzmin.flowersoflife.feature.auth.validators

import com.kuzmin.flowersoflife.core.domain.extensions.isEmailConsistent
import com.kuzmin.flowersoflife.core.domain.extensions.isPasswordConsistent
import com.kuzmin.flowersoflife.core.domain.model.aggregate.UserFamily
import com.kuzmin.flowersoflife.core.local.validators.ContextualValidator
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType

object RegisterUserContextualValidator : ContextualValidator<UserFamily, String, RegisterErrorType> {

    override fun validate(userFamily: UserFamily, repeatPassword: String): Set<RegisterErrorType> = buildSet {
        with(userFamily) {
            if (family.familyName.isBlank()) add(RegisterErrorType.FAMILY_NAME_EMPTY)

            if (user.name.isBlank()) add(RegisterErrorType.USERNAME_IS_EMPTY)

            if (user.email.isBlank()) add(RegisterErrorType.EMAIL_EMPTY)
            else if (!userFamily.user.email.isEmailConsistent()) add(RegisterErrorType.EMAIL_INVALID)

            if (userFamily.user.password.isBlank()) add(RegisterErrorType.PASSWORD_EMPTY)
            else if (!user.password.isPasswordConsistent()) add(RegisterErrorType.PASSWORD_WEAK)

            if (repeatPassword.isNotBlank() && user.password != repeatPassword) {
                add(RegisterErrorType.PASSWORD_MISMATCH)
            }

            if (user.role == null) {
                add(RegisterErrorType.ROLE_NOT_SELECTED)
            }
        }
    }
}