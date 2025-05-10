package com.kuzmin.flowersoflife.core.local.validators

interface Validator<T, E : ValidationErrorType> {

    fun validate(data: T): Set<E>
}