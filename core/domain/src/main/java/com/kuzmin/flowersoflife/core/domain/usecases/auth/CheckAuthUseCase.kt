package com.kuzmin.flowersoflife.core.domain.usecases.auth

interface CheckAuthUseCase {
    suspend operator fun invoke(): Boolean
}