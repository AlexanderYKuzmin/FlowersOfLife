package com.kuzmin.flowersoflife.core.domain.usecases

interface CheckAuthUseCase {
    suspend operator fun invoke(): Boolean
}