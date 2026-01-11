package com.practicum.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practicum.myapplication.screens.MainMenuScreen
import com.practicum.myapplication.screens.SearchScreen
import com.practicum.myapplication.screens.SettingsScreen
import androidx.compose.ui.Modifier



@Composable
fun PlaylistHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.MAIN_MENU.name,
        modifier = modifier
    ) {
        // Главное меню
        composable(NavigationScreen.MAIN_MENU.name) {
            MainMenuScreen(
                onSearchClick = { navigateToSearch(navController) },
                onPlaylistsClick = { /* будет позже */ },
                onFavoritesClick = { /* будет позже */ },
                onSettingsClick = { navigateToSettings(navController) }
            )
        }

        // Экран поиска
        composable(NavigationScreen.SEARCH.name) {
            SearchScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // Экран настроек
        composable(NavigationScreen.SETTINGS.name) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

// Методы навигации
fun navigateToSearch(navController: NavHostController) {
    navController.navigate(NavigationScreen.SEARCH.name)
}

fun navigateToSettings(navController: NavHostController) {
    navController.navigate(NavigationScreen.SETTINGS.name)
}

fun navigateToPlaylists(navController: NavHostController) {
    navController.navigate(NavigationScreen.PLAYLISTS.name)
}

fun navigateToFavorites(navController: NavHostController) {
    navController.navigate(NavigationScreen.FAVORITES.name)
}

fun navigateToMainMenu(navController: NavHostController) {
    navController.navigate(NavigationScreen.MAIN_MENU.name) {
        popUpTo(NavigationScreen.MAIN_MENU.name) { inclusive = true }
    }
}
