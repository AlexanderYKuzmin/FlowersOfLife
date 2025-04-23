package com.kuzmin.flowersoflife.data_provider.local_repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.EMAIL
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.FIRSTNAME
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.GROUP
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.IS_ADMIN
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.PASSWORD
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.ROLE
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.UID
import com.kuzmin.flowersoflife.feature.auth.api.PrefManager
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

    override suspend fun getUser(): User {
        with(UserDataScheme) {
            return dataStore.data.map { prefs ->
                val firstname = prefs[FIRSTNAME] ?: ""
                val group = prefs[GROUP] ?: ""
                val email = prefs[EMAIL] ?: ""
                val password = prefs[PASSWORD] ?: ""
                val role = prefs[ROLE]
                val isAdmin = prefs[IS_ADMIN] ?: false
                val uid = prefs[UID] ?: ""

                User(
                    firstName = firstname,
                    groupName = group,
                    email = email,
                    role = role?.let {
                        UserRole.valueOf(
                            role
                        )
                    },
                    isAdmin = isAdmin,
                    password = password,
                    uid = uid
                )
            }.first()
        }
    }

    override suspend fun saveUser(user: User) {
        with(user) {
            dataStore.edit { prefs ->
                prefs[FIRSTNAME] = firstName
                prefs[GROUP] = groupName
                prefs[EMAIL] = email
                prefs[PASSWORD] = password
                prefs[ROLE] = role?.name ?: throw RuntimeException("Role can't be null")
                prefs[IS_ADMIN] = isAdmin
                prefs[UID] = uid ?: ""
            }
        }
    }

    override suspend fun deleteUser() {
        dataStore.edit { it.clear() }
    }
}