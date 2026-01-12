package com.practicum.myapplication.screens

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.practicum.myapplication.R
import com.practicum.myapplication.model.SearchState
import com.practicum.myapplication.model.Track
import com.practicum.myapplication.ui.theme.MyApplicationTheme
import com.practicum.myapplication.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    onBackClick: () -> Unit = {},
    viewModel: SearchViewModel
) {
    val searchQuery = remember { mutableStateOf("") }
    val screenState by viewModel.searchScreenState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Заголовок с кнопкой назад и текстом
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .statusBarsPadding()
                .height(56.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                onClick = {
                    viewModel.resetState()   // сброс состояния
                    onBackClick()            // навигация назад
                },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }


            Text(
                text = "Поиск",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Поле поиска с иконками
        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { newValue ->
                searchQuery.value = newValue
                // Запускаем поиск в реальном времени
                if (newValue.isNotEmpty()) {
                    viewModel.searchTracks(newValue)
                } else {
                    // Если поле пусто, возвращаемся в Initial
                    viewModel.searchTracks("")
                }
            },
            placeholder = {
                Text(
                    text = "Поиск",
                    color = Color(0xFFBDBEC0),
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedContainerColor = Color(0xFFE8E8E8),
                focusedContainerColor = Color(0xFFE8E8E8),
                cursorColor = Color(0xFF3772E7),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                fontSize = 16.sp
            ),
            leadingIcon = {
                IconButton(
                    onClick = {
                        // Запускаем поиск
                        viewModel.searchTracks(searchQuery.value)
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFFBDBEC0),
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            trailingIcon = {
                if (searchQuery.value.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            searchQuery.value = ""
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color(0xFFBDBEC0),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            },
            singleLine = true
        )

        // Обработка состояний поиска
        when (screenState) {
            is SearchState.Initial -> {
                // Ничего не показываем
            }

            is SearchState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SearchState.Success -> {
                val tracks = (screenState as SearchState.Success).foundList
                if (tracks.isEmpty()) {
                    // Пустой результат
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 100.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_music),
                                contentDescription = "No tracks",
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(bottom = 16.dp),
                                tint = Color(0xFFD0D0D0)
                            )
                            Text(
                                text = "Ничего не нашлось",
                                fontSize = 16.sp,
                                color = Color(0xFFD0D0D0),
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(tracks.size) { index ->
                            TrackListItem(track = tracks[index])
                            if (index < tracks.size - 1) {
                                HorizontalDivider(
                                    thickness = 0.5.dp,
                                    color = Color(0xFFE0E0E0),
                                    modifier = Modifier.padding(vertical = 0.dp)
                                )
                            }
                        }
                    }
                }
            }

            is SearchState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ошибка: ${(screenState as SearchState.Error).error}",
                        color = Color.Red,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TrackListItem(track: Track) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Иконка трека
        Icon(
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = "Track",
            modifier = Modifier.size(40.dp),
            tint = Color.Black
        )

        // Информация о треке (название + артист + время)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(0.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = track.trackName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 1
            )
            Text(
                text = "${track.artistName} · ${track.trackTime}",
                fontSize = 14.sp,
                color = Color(0xFF999999),
                maxLines = 1
            )
        }

        // Символ стрелки
        Text(
            text = "❯",
            fontSize = 20.sp,
            color = Color(0xFFCCCCCC),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    MyApplicationTheme {
        // Для превью можно временно передать фейковый ViewModel,
        // либо вообще не вызывать SearchScreen с viewModel
    }
}
