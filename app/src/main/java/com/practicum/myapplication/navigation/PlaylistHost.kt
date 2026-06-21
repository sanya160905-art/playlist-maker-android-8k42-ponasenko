package com.practicum.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
                onSearchClick = { navController.navigate(NavigationScreen.SEARCH.name) },
                onPlaylistsClick = { navController.navigate(NavigationScreen.PLAYLISTS.name) },
                onFavoritesClick = { navController.navigate(NavigationScreen.FAVORITES.name) },
                onSettingsClick = { navController.navigate(NavigationScreen.SETTINGS.name) }
            )
        }

        composable(NavigationScreen.SEARCH.name) {
            if (searchViewModel != null) {
                SearchScreen(
                    viewModel = searchViewModel,
                    onBackClick = { navController.popBackStack() },
                    onTrackClick = { track ->
                        selectedTrack = track
                        navController.navigate(NavigationScreen.TRACK_DETAIL.name)
                    }
                )
            }
        }

        composable(NavigationScreen.TRACK_DETAIL.name) {
            selectedTrack?.let { track ->
                TrackDetailScreen(
                    track = track,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable(NavigationScreen.SETTINGS.name) {
            SettingsScreen(onBackClick = { navController.popBackStack() })
        }

        composable(NavigationScreen.PLAYLISTS.name) {
            PlaylistsScreen(
                onBackClick = { navController.popBackStack() },
                onCreatePlaylistClick = {
                    navController.navigate(NavigationScreen.CREATE_PLAYLIST.name)
                },
                onPlaylistClick = { playlistId ->
                    navController.navigate("${NavigationScreen.PLAYLIST_DETAIL.name}/$playlistId")
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
                onBackClick = { navController.popBackStack() },
                onTrackClick = { track ->
                    selectedTrack = track
                    navController.navigate(NavigationScreen.TRACK_DETAIL.name)
                }
            )
        }
        composable(NavigationScreen.FAVORITES.name) {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() },
                onTrackClick = { track ->
                    selectedTrack = track
                    navController.navigate(NavigationScreen.TRACK_DETAIL.name)
                }
            )
        }

        composable(NavigationScreen.CREATE_PLAYLIST.name) {
            CreatePlaylistScreen(
                onBackClick = { navController.popBackStack() },
                onPlaylistCreated = {
                    navController.popBackStack()
                }
            )
        }
    }
}