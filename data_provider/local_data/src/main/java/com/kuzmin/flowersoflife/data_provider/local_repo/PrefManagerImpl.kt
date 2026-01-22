package com.kuzmin.flowersoflife.data_provider.local_repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.kuzmin.flowersoflife.core.domain.model.Family
import com.kuzmin.flowersoflife.core.domain.model.User
import com.kuzmin.flowersoflife.core.domain.model.UserRole
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.data_provider.local_repo.FamilyDataScheme.FAMILY_CODE
import com.kuzmin.flowersoflife.data_provider.local_repo.FamilyDataScheme.FAMILY_NAME
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.EMAIL
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.FAMILY_ID
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.IS_ADMIN
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.NAME
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.ROLE
import com.kuzmin.flowersoflife.data_provider.local_repo.UserDataScheme.USER_ID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrefManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PrefManager {

    override suspend fun getUser(): User {
        with(UserDataScheme) {
            return dataStore.data.map { prefs ->
                val firstname = prefs[NAME] ?: ""
                val email = prefs[EMAIL] ?: ""
                val role = prefs[ROLE]
                val isAdmin = prefs[IS_ADMIN] ?: false
                val uid = prefs[USER_ID] ?: ""
                val familyId = prefs[FAMILY_ID] ?: ""

                User(
                    name = firstname,
                    email = email,
                    role = role?.let {
                        UserRole.valueOf(
                            role
                        )
                    },
                    isAdmin = isAdmin,
                    userId = uid,
                    familyId = familyId
                )
            }.first()
        }
    }

    override suspend fun saveUser(user: User) {
        with(user) {
            dataStore.edit { prefs ->
                prefs[NAME] = name
                prefs[EMAIL] = email
                prefs[ROLE] = role?.name ?: throw RuntimeException("Role can't be null")
                prefs[IS_ADMIN] = isAdmin
                prefs[USER_ID] = userId
                prefs[FAMILY_ID] = familyId
            }
        }
    }

    override suspend fun deleteUser() {
        dataStore.edit { it.clear() }
    }

    override suspend fun getFamily(): Family {
        with(FamilyDataScheme) {
            return dataStore.data.map { prefs ->
                val familyId = prefs[FAMILY_ID] ?: ""
                val name = prefs[FAMILY_NAME] ?: ""
                val familyCode = prefs[FAMILY_CODE] ?: ""

                Family(
                    familyId = familyId,
                    familyName = name,
                    familyCode = familyCode
                )
            }.first()
        }
    }

    override suspend fun saveFamily(family: Family) {
        with(family) {
            dataStore.edit { prefs ->
                prefs[FAMILY_ID] = familyId
                prefs[FAMILY_NAME] = familyName
                prefs[FAMILY_CODE] = familyCode
            }
        }
    }

    override suspend fun deleteFamily() {
        dataStore.edit { it.clear() }
    }
}