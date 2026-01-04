package com.kuzmin.flowersoflife.navigation.manager

import com.kuzmin.flowersoflife.core.navigation.NavigationManager
import com.kuzmin.flowersoflife.core.navigation.model.NavigationCommand
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Singleton

@Singleton
class NavigationManagerImpl : NavigationManager {
    private val _commands = MutableSharedFlow<NavigationCommand>()
    override val commands: SharedFlow<NavigationCommand> = _commands.asSharedFlow()

    override suspend fun navigate(command: NavigationCommand) {
        _commands.emit(command)
    }
}
