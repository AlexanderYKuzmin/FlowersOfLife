package com.kuzmin.flowersoflife.core.local.validators

interface Validator<T, C, E : ValidationErrorType> {
    fun validate(value: T, additional: C): Set<E>
}
