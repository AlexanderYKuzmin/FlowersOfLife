package com.kuzmin.flowersoflife.di

import com.kuzmin.flowersoflife.core.navigation.FeatureNavGraph
import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.routing.Route.AUTH_NAV_GRAPH
import com.kuzmin.flowersoflife.core.navigation.routing.Route.CHILD_NAV_GRAPH
import com.kuzmin.flowersoflife.core.navigation.routing.Route.PARENT_NAV_GRAPH
import com.kuzmin.flowersoflife.navigation.graph.AuthNavGraph
import com.kuzmin.flowersoflife.navigation.graph.ChildNavGraph
import com.kuzmin.flowersoflife.navigation.graph.ParentNavGraph
import com.kuzmin.flowersoflife.navigation.manager.NavigationManagerImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val navigationModule = module {
    single<FeatureNavGraph>(named(PARENT_NAV_GRAPH)) { ParentNavGraph() }
    single<FeatureNavGraph>(named(CHILD_NAV_GRAPH)) { ChildNavGraph() }
    single<FeatureNavGraph>(named(AUTH_NAV_GRAPH)) { AuthNavGraph() }

    single<Set<FeatureNavGraph>> {
        setOf(
            get(named(PARENT_NAV_GRAPH)),
            get(named(CHILD_NAV_GRAPH)),
            get(named(AUTH_NAV_GRAPH))
        )
    }

    single<NavigationManager> { NavigationManagerImpl() }
}