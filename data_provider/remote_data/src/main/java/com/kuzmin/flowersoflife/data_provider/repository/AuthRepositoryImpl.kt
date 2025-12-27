package com.kuzmin.flowersoflife.data_provider.repository

import com.google.firebase.auth.FirebaseUser
import com.kuzmin.flowersoflife.core.AuthService
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.data_provider.mapper.UserMapper
import com.kuzmin.flowersoflife.feature.auth.api.AuthRepository
import com.kuzmin.flowersoflife.feature.auth.domain.model.AuthCredentials

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val userMapper: UserMapper
) : AuthRepository {

    override fun getCurrentUser(): FirebaseUser? = authService.getCurrentUser()

    override suspend fun isUserAuthorized(): Boolean {
        return getCurrentUser() != null
    }

    override suspend fun signInWithEmail(authCredentials: AuthCredentials): Boolean {
        return authService.signInWithEmail(
            userMapper.mapAuthCredentialsToAuthCredentialsFb(authCredentials)
        )
    }

    override suspend fun registerWithEmail(user: User): User? {
        val userFb = authService.registerWithEmail(
            userMapper.mapUserToUserFb(user)
        )
        return userFb.uid?.let { userMapper.mapUserFbToUser(userFb) }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
        return authService.signInWithGoogle(idToken)
    }

    override fun signOut() {
        authService.signOut()
    }
}