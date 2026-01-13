package com.practicum.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.myapplication.screens.MainMenuScreen
import com.practicum.myapplication.screens.SearchScreen
import com.practicum.myapplication.screens.SettingsScreen
import com.practicum.myapplication.screens.PlaylistsScreen
import com.practicum.myapplication.screens.FavoritesScreen
import com.practicum.myapplication.viewmodel.SearchViewModel

@Composable
fun PlaylistHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel? = null
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.MAIN_MENU.name
    ) {
        composable(NavigationScreen.MAIN_MENU.name) {
            MainMenuScreen(
                onSearchClick = {
                    navController.navigate(NavigationScreen.SEARCH.name)
                },
                onPlaylistsClick = {
                    navController.navigate(NavigationScreen.PLAYLISTS.name)
                },
                onFavoritesClick = {
                    navController.navigate(NavigationScreen.FAVORITES.name)
                },
                onSettingsClick = {
                    navController.navigate(NavigationScreen.SETTINGS.name)
                }
            )
        }

        composable(NavigationScreen.SEARCH.name) {
            if (searchViewModel != null) {
                SearchScreen(
                    onBackClick = { navController.popBackStack() },
                    viewModel = searchViewModel
                )
            }
        }

        composable(NavigationScreen.SETTINGS.name) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(NavigationScreen.PLAYLISTS.name) {
            PlaylistsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(NavigationScreen.FAVORITES.name) {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
