package com.kuzmin.flowersoflife.core.navigation

import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import kotlinx.coroutines.flow.SharedFlow

interface NavigationManager {
    val commands: SharedFlow<NavigationCommand>

    suspend fun navigate(command: NavigationCommand)
}