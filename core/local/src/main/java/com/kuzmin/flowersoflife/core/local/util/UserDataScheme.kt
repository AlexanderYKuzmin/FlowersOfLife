package com.kuzmin.flowersoflife.core.local.util

import androidx.datastore.preferences.core.stringPreferencesKey

object UserDataScheme {
    val FIRSTNAME = stringPreferencesKey("firstname")
    val EMAIL = stringPreferencesKey("email")
    val PASSWORD = stringPreferencesKey("password")
    val ROLE = stringPreferencesKey("role")
    val UID = stringPreferencesKey("uid")
}