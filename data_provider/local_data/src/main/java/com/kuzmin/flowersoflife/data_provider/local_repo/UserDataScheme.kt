package com.kuzmin.flowersoflife.data_provider.local_repo

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserDataScheme {
    val FIRSTNAME = stringPreferencesKey("firstname")
    val GROUP = stringPreferencesKey("group")
    val EMAIL = stringPreferencesKey("email")
    val PASSWORD = stringPreferencesKey("password")
    val ROLE = stringPreferencesKey("role")
    val IS_ADMIN = booleanPreferencesKey("is_admin")
    val UID = stringPreferencesKey("uid")
}