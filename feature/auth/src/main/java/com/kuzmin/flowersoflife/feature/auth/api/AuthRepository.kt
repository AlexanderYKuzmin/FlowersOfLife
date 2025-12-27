package com.kuzmin.flowersoflife.feature.auth.api

import com.google.firebase.auth.FirebaseUser
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthCredentials

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?

    suspend fun isUserAuthorized(): Boolean

    suspend fun signInWithEmail(authCredentials: AuthCredentials): Boolean

    suspend fun registerWithEmail(user: User): User?

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>

    fun signOut()
}