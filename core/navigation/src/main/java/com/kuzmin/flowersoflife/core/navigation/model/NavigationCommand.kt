package com.kuzmin.flowersoflife.core.navigation.model

import android.os.Parcelable
import android.util.Log

sealed interface NavigationCommand {
    class ToDestination(
        private val destination: String,
        private val args: List<String>? = null
    ) : NavigationCommand {
        fun buildDestination(): String {
            Log.d("CAB-2-2", "ToDestination. buildDestination")
            return buildString {
                append(destination)
                args?.let {
                    append("/")
                    append(it.joinToString(separator = "/"))
                }
            }
        }
    }

    data class ToDestinationParcelable(
        val destination: String,
        val parcelableArgs: Map<String, Parcelable>? = null
    ) : NavigationCommand

    data class ToGraph(
        val targetRoute: String,
        val popUpTo: String,
        val inclusive: Boolean = false
    ) : NavigationCommand
    data class Back(val route: String? = null, val inclusive: Boolean = false) : NavigationCommand
    data class Replace(val destination: String, val popUpTo: String) : NavigationCommand
}

