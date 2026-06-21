package com.practicum.myapplication.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.practicum.myapplication.creator.Creator
import com.practicum.myapplication.navigation.PlaylistHost
import com.practicum.myapplication.ui.theme.MyApplicationTheme
import com.practicum.myapplication.viewmodel.SearchViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import kotlinx.coroutines.delay
import androidx.compose.foundation.interaction.MutableInteractionSource

class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModel.getViewModelFactory()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themePreferences = remember { Creator.getThemePreferences() }
            val isDarkTheme by themePreferences.isDarkTheme.collectAsState(initial = false)

            MyApplicationTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                val currentEntry by navController.currentBackStackEntryAsState()
                var isNavigationBlocked by remember { mutableStateOf(false) }

                LaunchedEffect(currentEntry) {
                    isNavigationBlocked = true
                    delay(600)
                    isNavigationBlocked = false
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        PlaylistHost(
                            navController = navController,
                            searchViewModel = searchViewModel
                        )

                        if (isNavigationBlocked) {
                            val interactionSource = remember { MutableInteractionSource() }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Transparent)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) {}
                            )
                        }
                    }
                }
            }
        }

    }
}