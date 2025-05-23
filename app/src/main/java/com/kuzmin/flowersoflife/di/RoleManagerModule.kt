package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.core.domain.roles.RoleManager
import com.kuzmin.flowersoflife.data.FirebaseRoleManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RoleManagerModule {

    @Binds
    fun bindRoleManager(roleManager: FirebaseRoleManagerImpl): RoleManager
}