package com.example.bottomnavigationexample.ui.component.bottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItemModel(
    var index : Int = 0,
    var label: String = "Home",
    var icon: ImageVector = Icons.Filled.Home,
    var route: String = "route_home"
) {
    fun getBottomNavigationItems() : List<BottomNavigationItemModel> =
        listOf(
            BottomNavigationItemModel(
                index = 0,
                label = "Home",
                icon = Icons.Filled.Home,
                route = BottomNavigationScreen.Home.route
            ),
            BottomNavigationItemModel(
                index = 1,
                label = "Search",
                icon = Icons.Filled.Search,
                route = BottomNavigationScreen.Search.route
            ),
            BottomNavigationItemModel(
                index = 2,
                label = "Profile",
                icon = Icons.Filled.AccountCircle,
                route = BottomNavigationScreen.Profile.route
            )
        )
}
