package com.kuzmin.flowersoflife.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.kuzmin.flowersoflife.common.ext.setValueSuspend
import com.kuzmin.flowersoflife.core.AuthService
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

    override suspend fun signInWithEmail(userFb: UserFb): UserFb {
        val result = firebaseAuth.signInWithEmailAndPassword(userFb.email, userFb.password).await()

        return userFb.copy(uid = result.user?.uid)
    }

    override suspend fun registerWithEmail(userFb: UserFb): UserFb {
        Log.d("registerWithEmail", "registerWithEmail start: $userFb")
        val result = firebaseAuth.createUserWithEmailAndPassword(userFb.email, userFb.password).await()
        Log.d("registerWithEmail", "result: $result")
        result.user?.uid?.let {
            val registeredUserFb = userFb.copy(uid = it)
            saveUserToDatabase(registeredUserFb)
        }

        Log.d("registerWithEmail", "registerWithEmail: ${result.user?.uid}")

        return userFb.copy(uid = result.user?.uid)
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