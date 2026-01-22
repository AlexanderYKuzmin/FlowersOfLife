package com.kuzmin.flowersoflife.data_provider.local_repo

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserDataScheme {
    val NAME = stringPreferencesKey("firstname")
    val EMAIL = stringPreferencesKey("email")
    val EMAIL_VERIFIED = booleanPreferencesKey("email_verified")
    val ROLE = stringPreferencesKey("role")
    val IS_ADMIN = booleanPreferencesKey("is_admin")
    val USER_ID = stringPreferencesKey("uid")
    val FAMILY_ID = stringPreferencesKey("family_id")
}

object FamilyDataScheme {
    val FAMILY_ID = stringPreferencesKey("family_id")
    val FAMILY_NAME = stringPreferencesKey("family_name")
    val FAMILY_CODE = stringPreferencesKey("family_code")
}