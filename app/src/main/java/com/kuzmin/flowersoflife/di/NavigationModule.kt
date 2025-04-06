package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.navigation.AuthNavGraph
import com.kuzmin.flowersoflife.navigation.ChildNavGraph
import com.kuzmin.flowersoflife.navigation.ParentNavGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    @IntoSet
    fun bindParentNavGraph(navGraph: ParentNavGraph): FeatureNavGraph

    @Binds
    @IntoSet
    fun bindChildNavGraph(navGraph: ChildNavGraph): FeatureNavGraph

    @Binds
    @IntoSet
    fun bindAuthNavGraph(navGraph: AuthNavGraph): FeatureNavGraph
}