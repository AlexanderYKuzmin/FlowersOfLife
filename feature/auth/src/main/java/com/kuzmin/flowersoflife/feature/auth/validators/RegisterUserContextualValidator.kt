package com.kuzmin.flowersoflife.feature.auth.validators

import com.kuzmin.flowersoflife.core.domain.extensions.isEmailConsistent
import com.kuzmin.flowersoflife.core.domain.extensions.isPasswordConsistent
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.local.validators.ContextualValidator
import com.kuzmin.flowersoflife.feature.auth.exception.errors.RegisterErrorType

object RegisterUserContextualValidator : ContextualValidator<User, String, RegisterErrorType> {

    override fun validate(user: User, repeatPassword: String): Set<RegisterErrorType> = buildSet {
        if (user.groupName.isBlank()) add(RegisterErrorType.GROUP_NAME_EMPTY)

        if (user.firstName.isBlank()) add(RegisterErrorType.FIRSTNAME_IS_EMPTY)

        if (user.email.isBlank()) add(RegisterErrorType.EMAIL_EMPTY)
        else if (!user.email.isEmailConsistent()) add(RegisterErrorType.EMAIL_INVALID)

        if (user.password.isBlank()) add(RegisterErrorType.PASSWORD_EMPTY)
        else if (!user.password.isPasswordConsistent()) add(RegisterErrorType.PASSWORD_WEAK)

        if (repeatPassword.isNotBlank() && user.password != repeatPassword) {
            add(RegisterErrorType.PASSWORD_MISMATCH)
        }

        if (user.role == null) {
            add(RegisterErrorType.ROLE_NOT_SELECTED)
        }
    }
}