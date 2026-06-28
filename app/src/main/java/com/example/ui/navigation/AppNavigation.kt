package com.example.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.MainViewModel
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.ListingsScreen
import com.example.ui.screens.OrdersScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StarterKitScreen
import com.example.ui.theme.*
import kotlinx.serialization.Serializable

@Serializable object DashboardRoute
@Serializable object ListingsRoute
@Serializable object OrdersRoute
@Serializable object StarterKitRoute
@Serializable object SettingsRoute

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

val topLevelRoutes = listOf(
    TopLevelRoute("Kit", StarterKitRoute, Icons.Filled.Star),
    TopLevelRoute("Market", DashboardRoute, Icons.Filled.Dashboard),
    TopLevelRoute("Listings", ListingsRoute, Icons.Filled.List),
    TopLevelRoute("Orders", OrdersRoute, Icons.Filled.ShoppingCart),
    TopLevelRoute("Config", SettingsRoute, Icons.Filled.Settings)
)

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = SurfaceVariant,
                contentColor = OnSurfaceVariant
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                topLevelRoutes.forEach { topLevelRoute ->
                    val selected = currentDestination?.hierarchy?.any { 
                        it.route?.contains(topLevelRoute.route::class.simpleName ?: "") == true 
                    } == true

                    NavigationBarItem(
                        icon = { Icon(topLevelRoute.icon, contentDescription = topLevelRoute.name) },
                        label = { Text(topLevelRoute.name) },
                        selected = selected,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = OnPrimaryContainer,
                            unselectedIconColor = OnSurfaceVariant,
                            selectedTextColor = OnSurface,
                            unselectedTextColor = OnSurfaceVariant,
                            indicatorColor = PrimaryContainer
                        ),
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = StarterKitRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<StarterKitRoute> {
                StarterKitScreen()
            }
            composable<DashboardRoute> {
                DashboardScreen(viewModel, onNavigateToListings = { navController.navigate(ListingsRoute) })
            }
            composable<ListingsRoute> {
                ListingsScreen(viewModel)
            }
            composable<OrdersRoute> {
                OrdersScreen(viewModel)
            }
            composable<SettingsRoute> {
                SettingsScreen()
            }
        }
    }
}
