package com.kuzmin.flowersoflife.core.local.validators

interface Validator<T> {
    fun validate(value: T): Map<ValidationErrorType, String>
}