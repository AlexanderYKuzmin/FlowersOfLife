package com.kuzmin.flowersoflife

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class FirebaseDatabaseTest {

    private lateinit var database: DatabaseReference

    @Before
    fun setup() {
        database = FirebaseDatabase.getInstance().reference
    }

    @Test
    fun testWriteAndReadData() {
        val userId = "test_user"
        val userData = mapOf(
            "name" to "Alice",
            "age" to 10,
            "score" to 100
        )

        val latch = CountDownLatch(1) // Синхронизация теста

        database.child("users").child(userId).setValue(userData)
            .addOnSuccessListener {
                Log.d("FirebaseTest", "Данные успешно записаны")
            }
            .addOnFailureListener { e ->
                fail("Ошибка записи: ${e.message}")
            }

        database.child("users").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val name = snapshot.child("name").getValue(String::class.java)
                    val age = snapshot.child("age").getValue(Int::class.java)
                    val score = snapshot.child("score").getValue(Int::class.java)

                    assertEquals("Alice", name)
                    assertEquals(10, age)
                    assertEquals(100, score)

                    Log.d("FirebaseTest", "Данные успешно прочитаны: Имя=$name, Возраст=$age, Очки=$score")
                } else {
                    fail("Данные отсутствуют")
                }
                latch.countDown()
            }

            override fun onCancelled(error: DatabaseError) {
                fail("Ошибка чтения: ${error.message}")
                latch.countDown()
            }
        })

        // Ждем завершения теста (не более 5 секунд)
        assertTrue(latch.await(5, TimeUnit.SECONDS))
    }
}