package com.kuzmin.flowersoflife.data_provider.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.kuzmin.flowersoflife.core.domain.storage.PrefManager
import com.kuzmin.flowersoflife.data_provider.local_repo.PrefManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File

val localDataProviderModule = module {
    single<DataStore<Preferences>> {
        val context: Context = androidContext()
        PreferenceDataStoreFactory.create(
            produceFile = {
                File(context.filesDir, "datastore/user_preferences.preferences_pb")
            }
        )
    }

    single<PrefManager> { PrefManagerImpl(get()) }
}