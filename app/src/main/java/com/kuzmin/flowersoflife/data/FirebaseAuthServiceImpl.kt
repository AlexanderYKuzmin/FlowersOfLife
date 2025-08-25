package com.kuzmin.flowersoflife.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.kuzmin.flowersoflife.common.ext.setValueSuspend
import com.kuzmin.flowersoflife.core.AuthService
import com.kuzmin.flowersoflife.core.model.AuthCredentialsFb
import com.kuzmin.flowersoflife.core.model.UserFb
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : AuthService {

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    override suspend fun signInWithEmail(authCredentialsFb: AuthCredentialsFb): Boolean {
        with(authCredentialsFb) {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return result.user != null
        }
    }

    override suspend fun registerWithEmail(userFb: UserFb): UserFb {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(userFb.email, userFb.password).await()

            result.user?.uid?.let {
                val registeredUserFb = userFb.copy(uid = it)
                saveUserToDatabase(registeredUserFb)
            }

            userFb.copy(uid = result.user?.uid)
        } catch (e: Exception) {
            throw e // TODO обернуть в кастом исключение и пробросить дальше
        }
    }


    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    private suspend fun saveUserToDatabase(userFb: UserFb) {

        firebaseDatabase.getReference("users")
            .child(userFb.uid!!)
            .setValueSuspend(userFb)

    }
}