package com.kuzmin.flowersoflife.data_provider.repository

import com.google.firebase.auth.FirebaseUser
import com.kuzmin.flowersoflife.core.api.AuthService
import com.kuzmin.flowersoflife.core.domain.model.AuthCredentials
import com.kuzmin.flowersoflife.data_provider.mapper.toAuthCredentialsFb
import com.kuzmin.flowersoflife.feature.api.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository {

    override fun getCurrentUser(): FirebaseUser? = authService.getCurrentUser()

    override suspend fun isUserAuthorized(): Boolean {
        return getCurrentUser() != null
    }

    override suspend fun signInWithEmail(authCredentials: AuthCredentials): Boolean {
        return authService.signInWithEmail(
            authCredentials.toAuthCredentialsFb()
        )
    }

    override suspend fun registerWithEmail(authCredentials: AuthCredentials): String? {
        return authService.registerWithEmail(
            authCredentials.toAuthCredentialsFb()
        )
    }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
        return authService.signInWithGoogle(idToken)
    }

    override fun signOut() {
        authService.signOut()
    }
}