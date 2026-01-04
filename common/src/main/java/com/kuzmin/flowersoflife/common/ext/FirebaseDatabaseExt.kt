package com.kuzmin.flowersoflife.common.ext

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun DatabaseReference.awaitSingleValueEvent(): DataSnapshot {
    return suspendCancellableCoroutine { continuation ->

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resume(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(error.toException())
            }
        }

        addListenerForSingleValueEvent(listener)

        continuation.invokeOnCancellation { removeEventListener(listener) }
    }
}

suspend fun Query.awaitSingleValueEvent(): DataSnapshot {
    return suspendCancellableCoroutine { continuation ->

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resume(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(error.toException())
            }
        }

        addListenerForSingleValueEvent(listener)

        continuation.invokeOnCancellation { removeEventListener(listener) }
    }
}

suspend fun DatabaseReference.setValueSuspend(value: Any): Void? =
    suspendCancellableCoroutine { continuation ->
        setValue(value).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result) {}
            } else {
                continuation.resumeWithException(task.exception ?: Exception("Failed to set value"))
            }
        }
    }


suspend fun DatabaseReference.getValueSuspend(): DataSnapshot =
    suspendCancellableCoroutine { continuation ->
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                continuation.resume(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                continuation.resumeWithException(error.toException())
            }
        })
    }

