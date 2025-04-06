package com.kuzmin.flowersoflife.data_provider.local_repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.local.util.UserDataScheme
import com.kuzmin.flowersoflife.core.local.util.UserDataScheme.EMAIL
import com.kuzmin.flowersoflife.core.local.util.UserDataScheme.FIRSTNAME
import com.kuzmin.flowersoflife.core.local.util.UserDataScheme.PASSWORD
import com.kuzmin.flowersoflife.core.local.util.UserDataScheme.ROLE
import com.kuzmin.flowersoflife.core.local.util.UserDataScheme.UID
import com.kuzmin.flowersoflife.data_provider.api.PrefManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrefManagerImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : PrefManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    private val dataStore = context.dataStore

    override suspend fun getUser(): com.kuzmin.flowersoflife.core.domain.model.User {
        with(UserDataScheme) {
            return dataStore.data.map { prefs ->
                val firstname = prefs[FIRSTNAME] ?: ""
                val email = prefs[EMAIL] ?: ""
                val password = prefs[PASSWORD] ?: ""
                val role = prefs[ROLE]
                val uid = prefs[UID] ?: ""

                com.kuzmin.flowersoflife.core.domain.model.User(
                    firstName = firstname,
                    email = email,
                    role = role?.let {
                        com.kuzmin.flowersoflife.core.domain.model.UserRole.valueOf(
                            role
                        )
                    },
                    password = password,
                    uid = uid
                )
            }.first()
        }
    }

    override suspend fun saveUser(user: com.kuzmin.flowersoflife.core.domain.model.User) {
        with(user) {
            dataStore.edit { prefs ->
                prefs[FIRSTNAME] = firstName
                prefs[EMAIL] = email
                prefs[PASSWORD] = password
                prefs[ROLE] = role?.name ?: throw RuntimeException("Role can't be null")
                prefs[UID] = uid ?: ""
            }
        }
    }

    override suspend fun deleteUser() {
        dataStore.edit { it.clear() }
    }
}