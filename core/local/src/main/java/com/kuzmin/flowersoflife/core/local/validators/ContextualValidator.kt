package com.kuzmin.flowersoflife.core.local.validators

interface ContextualValidator<T, C, E : ValidationErrorType> {

    fun validate(data: T, additional: C): Set<E>
}
