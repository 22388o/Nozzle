package com.kaiwolfram.nozzle.ui.app.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

class NozzleNavActions(navController: NavHostController) {
    val navigateToPersonalProfile: () -> Unit = {
        navController.navigate(NozzleRoute.PERSONAL_PROFILE) {
            setNavOptionsWithPop(navController = navController, optionsBuilder = this)
        }
    }

    val navigateToEditProfile: () -> Unit = {
        navController.navigate(NozzleRoute.EDIT_PROFILE) {
            setSimpleNavOptions(optionsBuilder = this)
        }
    }

    val navigateToFeed: () -> Unit = {
        navController.navigate(NozzleRoute.FEED) {
            setNavOptionsWithPop(navController = navController, optionsBuilder = this)
        }
    }

    val navigateToChat: () -> Unit = {
        navController.navigate(NozzleRoute.CHAT) {
            setNavOptionsWithPop(navController = navController, optionsBuilder = this)
        }
    }

    val navigateToKeys: () -> Unit = {
        navController.navigate(NozzleRoute.KEYS) {
            setNavOptionsWithPop(navController = navController, optionsBuilder = this)
        }
    }

    val navigateToRelays: () -> Unit = {
        navController.navigate(NozzleRoute.RELAYS) {
            setNavOptionsWithPop(navController = navController, optionsBuilder = this)
        }
    }

    private fun setNavOptionsWithPop(
        navController: NavHostController,
        optionsBuilder: NavOptionsBuilder
    ) {
        optionsBuilder.apply {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            setSimpleNavOptions(this)
        }
    }

    private fun setSimpleNavOptions(optionsBuilder: NavOptionsBuilder) {
        optionsBuilder.apply {
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
        }
    }
}
