package com.kuzmin.flowersoflife.common.ext

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

suspend fun FirebaseAuth.createUserWithEmailAndPasswordSuspend(email: String, password: String): AuthResult =
    suspendCancellableCoroutine { continuation ->
        createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(task.result) {}
                } else {
                    continuation.resumeWithException(task.exception ?: Exception("Unknown error"))
                }
            }
    }

suspend fun FirebaseAuth.signInWithEmailAndPasswordSuspend(email: String, password: String): AuthResult =
    suspendCancellableCoroutine { continuation ->
        signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(task.result) {}
                } else {
                    continuation.resumeWithException(task.exception ?: Exception("Unknown error"))
                }
            }
    }