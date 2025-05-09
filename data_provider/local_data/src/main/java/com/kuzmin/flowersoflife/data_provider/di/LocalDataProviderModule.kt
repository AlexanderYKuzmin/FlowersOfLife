package com.kuzmin.flowersoflife.data_provider.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.kuzmin.flowersoflife.feature.auth.api.PrefManager
import com.kuzmin.flowersoflife.data_provider.local_repo.PrefManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataProviderModule {

    @Binds
    fun bindPrefManager(prefManager: PrefManagerImpl): PrefManager

    companion object {
        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = {
                    File(context.filesDir, "datastore/user_preferences.preferences_pb")
                }
            )
        }
    }
}