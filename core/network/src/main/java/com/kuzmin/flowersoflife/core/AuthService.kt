package com.kuzmin.flowersoflife.core

import com.google.firebase.auth.FirebaseUser
import com.kuzmin.flowersoflife.core.model.UserFb

interface AuthService {
    fun getCurrentUser(): FirebaseUser?

    suspend fun signInWithEmail(userFb: UserFb): UserFb

    suspend fun registerWithEmail(userFb: UserFb): UserFb

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>

    fun signOut()
}