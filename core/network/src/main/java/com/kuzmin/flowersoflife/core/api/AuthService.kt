package com.kuzmin.flowersoflife.core.api

import com.google.firebase.auth.FirebaseUser
import com.kuzmin.flowersoflife.core.model.firebase.AuthCredentialsFb

interface AuthService {
    fun getCurrentUser(): FirebaseUser?

    suspend fun signInWithEmail(authCredentialsFb: AuthCredentialsFb): Boolean

    suspend fun registerWithEmail(authCredentialsFb: AuthCredentialsFb): String?

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>

    fun signOut()
}