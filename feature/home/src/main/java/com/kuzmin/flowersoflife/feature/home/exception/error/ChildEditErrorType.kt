package com.kuzmin.flowersoflife.feature.home.exception.error

import com.kuzmin.flowersoflife.core.local.validators.ValidationErrorType

enum class ChildEditErrorType(override val code: String) : ValidationErrorType {
    CHILD_NAME_EMPTY("child_name_empty"),
}