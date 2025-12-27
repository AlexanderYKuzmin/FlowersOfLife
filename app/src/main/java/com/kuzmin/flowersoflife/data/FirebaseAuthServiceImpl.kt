package com.kuzmin.flowersoflife.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kuzmin.flowersoflife.core.AuthService
import com.kuzmin.flowersoflife.core.model.AuthCredentialsFb
import com.kuzmin.flowersoflife.core.model.UserFb
import kotlinx.coroutines.tasks.await

class FirebaseAuthServiceImpl(
    private val firebaseAuth: FirebaseAuth,
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

            //TODO СОхранить токен в хранилище и отправить юзера на бэк в базу
            result.user?.let {
                //val registeredUserFb = userFb.copy(uid = it)
                    //saveUserToDatabase(registeredUserFb)
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
}