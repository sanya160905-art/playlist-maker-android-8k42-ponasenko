package com.practicum.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.creator.Creator
import com.practicum.myapplication.model.Track
import kotlinx.coroutines.launch
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.practicum.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailScreen(
    track: Track,
    onBackClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val tracksRepository = remember { Creator.getTracksRepository() }
    val playlistsRepository = remember { Creator.getPlaylistsRepository() }

    val trackFromDb by tracksRepository.getTrackByNameAndArtist(track).collectAsState(initial = null)
    val isFavorite = trackFromDb?.isFavorite ?: false

    val playlists by playlistsRepository.getAllPlaylists().collectAsState(initial = emptyList())

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top bar — только стрелка назад
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .statusBarsPadding()
                .height(40.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            // Обложка во всю ширину, квадратная, со скруглёнными углами
            AsyncImage(
                model = track.artworkUrl,
                contentDescription = track.trackName,
                placeholder = painterResource(id = R.drawable.ic_music),
                error = painterResource(id = R.drawable.ic_music),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Название и исполнитель — слева
            Text(
                text = track.trackName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = track.artistName,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Круглые кнопки: плейлист слева, избранное справа
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_to_playlist),
                        contentDescription = "Добавить в плейлист",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp)
                    )
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            tracksRepository.updateTrackFavoriteStatus(track, !isFavorite)
                        }
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_favorite_circle),
                        contentDescription = "В избранное",
                        tint = if (isFavorite) Color.Red else Color(0xFFCCCCCC),
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Длительность
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Длительность",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = track.trackTime,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Добавить в плейлист",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                HorizontalDivider(color = Color(0xFFE0E0E0))

                Spacer(modifier = Modifier.height(16.dp))

                if (playlists.isEmpty()) {
                    Text(
                        text = "Плейлистов пока нет",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 240.dp)
                    ) {
                        items(playlists.size) { index ->
                            val playlist = playlists[index]
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        coroutineScope.launch {
                                            tracksRepository.insertTrackToPlaylist(track, playlist.id)
                                            showBottomSheet = false
                                        }
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🎵", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(text = playlist.name, fontSize = 16.sp, color = Color.Black)
                                    Text(
                                        text = "${playlist.tracks.size} треков",
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { showBottomSheet = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3772E7)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Закрыть")
                }
            }
        }
    }
}