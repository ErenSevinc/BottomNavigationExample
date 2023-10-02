package com.example.bottomnavigationexample.ui.component.bottomNavigation

sealed class BottomNavigationScreen (val route: String) {
    object Home : BottomNavigationScreen("route_home")
    object Search : BottomNavigationScreen("route_search")
    object Profile : BottomNavigationScreen("route_profile")
}
