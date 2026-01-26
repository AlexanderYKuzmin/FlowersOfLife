package com.kuzmin.flowersoflife.feature.api.usecases.user.remote

import com.google.firebase.auth.FirebaseUser

interface GetCurrentFbUserUseCase {
    suspend operator fun invoke(): FirebaseUser?
}