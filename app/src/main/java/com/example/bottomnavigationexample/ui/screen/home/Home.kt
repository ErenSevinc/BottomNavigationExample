package com.example.bottomnavigationexample.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = viewModel<HomeViewModel>()

    val isLoading  by viewModel.isLoading.collectAsState()
    val viewState by viewModel.viewData.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading ?: false)

    LaunchedEffect(viewState) {
        if (viewState == null) {
            viewModel.updateList()
        } else {
            Log.e("xx","xxxxxx")
        }
    }
    with(viewState) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = viewModel::updateList
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(this@with?.reversed() ?: emptyList()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(120.dp)
                            .padding(start = 4.dp)
                    ) {
                        Text(text = it.user ?: "", fontWeight = FontWeight.Bold)
                        Text(text = it.post ?: "")
                    }
                }
            }
        }
    }

}