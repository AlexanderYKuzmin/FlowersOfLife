package com.kuzmin.flowersoflife.feature.api.usecases.home

interface DeleteChildRemoteUseCase {
    suspend operator fun invoke(childId: String)
}