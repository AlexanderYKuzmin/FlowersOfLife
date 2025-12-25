package com.kuzmin.flowersoflife

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

// 🔹 Простые mock-классы для тестов
data class MockUserFb(
    val uid: String? = null,
    val firstName: String = "TestUser",
    val email: String = "test@example.com",
    val role: String = "PARENT",
    val password: String = "123456",
    val groupName: String = "G1",
    val isAdmin: Boolean = true
)

data class MockAuthCredentialsFb(
    val email: String = "test@example.com",
    val password: String = "123456"
)

@RunWith(AndroidJUnit4::class)
class FirebaseDatabaseTest {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    @Before
    fun setup() {
        database = FirebaseDatabase.getInstance().reference.child("users")
        auth = FirebaseAuth.getInstance()

        // Удалим старые тестовые записи по uid
        val latch = CountDownLatch(1)
        database.orderByKey()
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { child ->
                        val uid = child.key.orEmpty()
                        if (uid.startsWith("uid_test_")) {
                            child.ref.removeValue()
                        }
                    }
                    latch.countDown()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseTest", "Очистка отменена: ${error.message}")
                    latch.countDown()
                }
            })
        assertTrue("Очистка не завершена", latch.await(5, TimeUnit.SECONDS))
    }

    @Test
    fun testWriteAndReadData() {
        val userId = "test_user_static"
        val userData = mapOf(
            "name" to "Alice",
            "age" to 10,
            "score" to 100
        )
        val latch = CountDownLatch(1)

        database.child(userId).setValue(userData)
            .addOnFailureListener { fail("Ошибка записи: ${it.message}") }

        database.child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("name").getValue(String::class.java)
                    val age = snapshot.child("age").getValue(Int::class.java)
                    val score = snapshot.child("score").getValue(Int::class.java)

                    assertEquals("Alice", name)
                    assertEquals(10, age)
                    assertEquals(100, score)
                    latch.countDown()
                }

                override fun onCancelled(error: DatabaseError) {
                    fail("Ошибка чтения: ${error.message}")
                    latch.countDown()
                }
            })

        assertTrue("Истекло время ожидания", latch.await(5, TimeUnit.SECONDS))
    }

    @Test
    fun testFirebaseRegistration() {
        val latch = CountDownLatch(1)
        val timestamp = System.currentTimeMillis()
        val email = "test_$timestamp@example.com"
        val password = "123456"
        val uidTestPrefix = "uid_test_$timestamp"

        val credentials = MockAuthCredentialsFb(email, password)
        val user = MockUserFb(
            uid = uidTestPrefix,
            email = credentials.email,
            password = credentials.password
        )

        auth.createUserWithEmailAndPassword(credentials.email, credentials.password)
            .addOnSuccessListener { authResult ->
                val uid = authResult.user?.uid ?: return@addOnSuccessListener fail("UID is null")
                val userForDb = user.copy(uid = uid)

                database.child(uid).setValue(userForDb)
                    .addOnSuccessListener {
                        database.child(uid)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val dbUser = snapshot.getValue(MockUserFb::class.java)
                                    assertNotNull(dbUser)
                                    assertEquals(user.firstName, dbUser?.firstName)
                                    assertEquals(user.email, dbUser?.email)
                                    assertEquals(user.role, dbUser?.role)
                                    assertEquals(user.isAdmin, dbUser?.isAdmin)
                                    latch.countDown()
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    fail("Ошибка чтения: ${error.message}")
                                    latch.countDown()
                                }
                            })
                    }
                    .addOnFailureListener {
                        fail("Ошибка записи: ${it.message}")
                        latch.countDown()
                    }
            }
            .addOnFailureListener {
                fail("Ошибка регистрации: ${it.message}")
                latch.countDown()
            }

        assertTrue("Истекло время ожидания регистрации", latch.await(10, TimeUnit.SECONDS))
    }
}
