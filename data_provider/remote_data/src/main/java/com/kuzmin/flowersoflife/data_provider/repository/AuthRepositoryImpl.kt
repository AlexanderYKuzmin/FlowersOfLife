package com.kuzmin.flowersoflife.data_provider.repository

import com.kuzmin.flowersoflife.core.domain.roles.RoleManager
import com.google.firebase.auth.FirebaseUser
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.AuthService
import com.kuzmin.flowersoflife.data_provider.api.AuthRepository
import com.kuzmin.flowersoflife.data_provider.mapper.UserMapper
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val roleManager: RoleManager,
    private val userMapper: UserMapper
) : AuthRepository {

    override fun getCurrentUser(): FirebaseUser? = authService.getCurrentUser()

    override suspend fun isUserAuthorized(): Boolean {
        return getCurrentUser() != null
    }

    override suspend fun signInWithEmail(user: User): Boolean {
        val result = authService.signInWithEmail(
            userMapper.mapUserToUserFb(user)
        )
        return result.uid != null
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

    override suspend fun getUserRole(userId: String): String? { //надо подумать , может exception выкинуть на null
        return roleManager.getUserRole(userId)
    }
}