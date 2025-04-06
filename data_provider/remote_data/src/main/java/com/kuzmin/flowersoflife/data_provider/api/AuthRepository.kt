package com.kuzmin.flowersoflife.data_provider.api

import com.google.firebase.auth.FirebaseUser
import com.kuzmin.flowersoflife.core.domain.model.User

interface AuthRepository {
    fun getCurrentUser(): FirebaseUser?

    suspend fun isUserAuthorized(): Boolean

    suspend fun signInWithEmail(user: User): Boolean

    suspend fun registerWithEmail(user: User): User?

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>

    fun signOut()

    suspend fun getUserRole(userId: String): String?
}