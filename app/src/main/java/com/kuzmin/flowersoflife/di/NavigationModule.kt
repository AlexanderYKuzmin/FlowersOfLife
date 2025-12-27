package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.navigation.graph.AuthNavGraph
import com.kuzmin.flowersoflife.navigation.graph.ChildNavGraph
import com.kuzmin.flowersoflife.navigation.graph.ParentNavGraph
import com.kuzmin.flowersoflife.navigation.manager.NavigationManagerImpl
import org.koin.dsl.module

val navigationModule = module {
    single<FeatureNavGraph> { ParentNavGraph() }
    single<FeatureNavGraph> { ChildNavGraph() }
    single<FeatureNavGraph> { AuthNavGraph() }

    single<NavigationManager> { NavigationManagerImpl() }
}