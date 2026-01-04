package com.kuzmin.flowersoflife.feature.api.repository

import com.google.firebase.auth.FirebaseUser
import com.kuzmin.flowersoflife.core.domain.model.AuthCredentials

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?

    suspend fun isUserAuthorized(): Boolean

    suspend fun signInWithEmail(authCredentials: AuthCredentials): Boolean

    suspend fun registerWithEmail(authCredentials: AuthCredentials): String?

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>

    fun signOut()
}