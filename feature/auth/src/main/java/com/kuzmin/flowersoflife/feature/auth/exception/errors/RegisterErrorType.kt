package com.kuzmin.flowersoflife.feature.auth.exception.errors

import com.kuzmin.flowersoflife.core.local.validators.ValidationErrorType

enum class RegisterErrorType(override val code: String) : ValidationErrorType {
    GROUP_NAME_EMPTY("group_name_empty"),
    FIRSTNAME_IS_EMPTY("firstname_is_empty"),
    EMAIL_EMPTY("email_empty"),
    EMAIL_INVALID("email_invalid"),
    PASSWORD_EMPTY("password_empty"),
    PASSWORD_WEAK("password_weak"),
    PASSWORD_MISMATCH("password_mismatch"),
    ROLE_NOT_SELECTED("role_not_selected"),
}
