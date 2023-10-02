package com.example.bottomnavigationexample.ui.component.bottomNavigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bottomnavigationexample.ui.screen.home.HomeScreen
import com.example.bottomnavigationexample.ui.screen.profile.ProfileScreen
import com.example.bottomnavigationexample.ui.screen.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    var selectedBottomNavigationScreen by remember { mutableStateOf(BottomNavigationItemModel()) }
    var toolbarTitle by remember { mutableStateOf("Home") }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = toolbarTitle, textAlign = TextAlign.Right, fontWeight = FontWeight.Bold)
                }
            )
        },
        bottomBar = {
            NavigationBar {
                BottomNavigationItemModel().getBottomNavigationItems()
                    .forEachIndexed { index, bottomNavigationItemModel ->
                        NavigationBarItem(
                            selected = index == selectedBottomNavigationScreen.index,
                            label = {
                                Text(text = bottomNavigationItemModel.label)
                            },
                            icon = {
                                Icon(
                                    imageVector = bottomNavigationItemModel.icon,
                                    contentDescription = bottomNavigationItemModel.label
                                )
                            },
                            onClick = {
                                selectedBottomNavigationScreen = bottomNavigationItemModel
                                toolbarTitle = selectedBottomNavigationScreen.label
                                navController.navigate(bottomNavigationItemModel.route) {
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
    ) {
        NavHost(
            navController = navController,
            startDestination = BottomNavigationScreen.Home.route,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            composable(BottomNavigationScreen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(BottomNavigationScreen.Search.route) {
                SearchScreen(navController = navController)
            }
            composable(BottomNavigationScreen.Profile.route) {
                ProfileScreen(navController = navController)
            }
        }
    }
}