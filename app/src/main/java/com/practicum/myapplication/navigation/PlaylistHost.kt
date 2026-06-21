package com.practicum.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.practicum.myapplication.model.Track
import com.practicum.myapplication.screens.MainMenuScreen
import com.practicum.myapplication.screens.SearchScreen
import com.practicum.myapplication.screens.SettingsScreen
import com.practicum.myapplication.screens.PlaylistsScreen
import com.practicum.myapplication.screens.PlaylistScreen
import com.practicum.myapplication.screens.FavoritesScreen
import com.practicum.myapplication.screens.TrackDetailScreen
import com.practicum.myapplication.screens.CreatePlaylistScreen
import com.practicum.myapplication.viewmodel.SearchViewModel
import com.practicum.myapplication.viewmodel.PlaylistViewModel

private fun safePopBackStack(navController: NavHostController) {
    if (navController.previousBackStackEntry != null) {
        navController.popBackStack()
    }
}

// launchSingleTop = true гарантирует, что повторный быстрый клик
// не создаст дублирующую запись в стеке навигации
private fun safeNavigate(navController: NavHostController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
    }
}

@Composable
fun PlaylistHost(
    navController: NavHostController,
    searchViewModel: SearchViewModel? = null
) {
    var selectedTrack: Track? = null

    NavHost(
        navController = navController,
        startDestination = NavigationScreen.MAIN_MENU.name
    ) {
        composable(NavigationScreen.MAIN_MENU.name) {
            MainMenuScreen(
                onSearchClick = { safeNavigate(navController, NavigationScreen.SEARCH.name) },
                onPlaylistsClick = { safeNavigate(navController, NavigationScreen.PLAYLISTS.name) },
                onFavoritesClick = { safeNavigate(navController, NavigationScreen.FAVORITES.name) },
                onSettingsClick = { safeNavigate(navController, NavigationScreen.SETTINGS.name) }
            )
        }

        composable(NavigationScreen.SEARCH.name) {
            if (searchViewModel != null) {
                SearchScreen(
                    viewModel = searchViewModel,
                    onBackClick = { safePopBackStack(navController) },
                    onTrackClick = { track ->
                        selectedTrack = track
                        safeNavigate(navController, NavigationScreen.TRACK_DETAIL.name)
                    }
                )
            }
        }

        composable(NavigationScreen.TRACK_DETAIL.name) {
            selectedTrack?.let { track ->
                TrackDetailScreen(
                    track = track,
                    onBackClick = { safePopBackStack(navController) }
                )
            }
        }

        composable(NavigationScreen.SETTINGS.name) {
            SettingsScreen(onBackClick = { safePopBackStack(navController) })
        }

        composable(NavigationScreen.PLAYLISTS.name) {
            PlaylistsScreen(
                onBackClick = { safePopBackStack(navController) },
                onCreatePlaylistClick = {
                    safeNavigate(navController, NavigationScreen.CREATE_PLAYLIST.name)
                },
                onPlaylistClick = { playlistId ->
                    safeNavigate(navController, "${NavigationScreen.PLAYLIST_DETAIL.name}/$playlistId")
                }
            )
        }

        composable(
            route = "${NavigationScreen.PLAYLIST_DETAIL.name}/{playlistId}",
            arguments = listOf(
                navArgument("playlistId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            val viewModel: PlaylistViewModel = viewModel(
                factory = PlaylistViewModel.getViewModelFactory(playlistId)
            )
            PlaylistScreen(
                viewModel = viewModel,
                onBackClick = { safePopBackStack(navController) },
                onTrackClick = { track ->
                    selectedTrack = track
                    safeNavigate(navController, NavigationScreen.TRACK_DETAIL.name)
                }
            )
        }
        composable(NavigationScreen.FAVORITES.name) {
            FavoritesScreen(
                onBackClick = { safePopBackStack(navController) },
                onTrackClick = { track ->
                    selectedTrack = track
                    safeNavigate(navController, NavigationScreen.TRACK_DETAIL.name)
                }
            )
        }

        composable(NavigationScreen.CREATE_PLAYLIST.name) {
            CreatePlaylistScreen(
                onBackClick = { safePopBackStack(navController) },
                onPlaylistCreated = {
                    safePopBackStack(navController)
                }
            )
        }
    }
}