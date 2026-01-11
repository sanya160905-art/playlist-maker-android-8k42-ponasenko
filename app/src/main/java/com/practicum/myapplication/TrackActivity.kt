package com.practicum.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.myapplication.model.TrackModel
import com.practicum.myapplication.model.TrackScreenState
import com.practicum.myapplication.ui.theme.MyApplicationTheme
import com.practicum.myapplication.viewmodel.TrackViewModel

class TrackActivity : ComponentActivity() {

    private val viewModel: TrackViewModel by viewModels {
        TrackViewModel.getViewModelFactory("123")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                TrackScreen(viewModel)
            }
        }
    }
}

@Composable
fun TrackScreen(viewModel: TrackViewModel) {
    // Подписываемся на состояние экрана
    val screenState by viewModel.trackScreenState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        when (screenState) {
            is TrackScreenState.Loading -> {
                // Показываем загрузку
                CircularProgressIndicator()
            }

            is TrackScreenState.Content -> {
                // Показываем контент
                val content = screenState as TrackScreenState.Content
                TrackScreenContent(trackModel = content.trackModel)
            }
        }
    }
}

@Composable
fun TrackScreenContent(trackModel: TrackModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Обложка трека
        AsyncImage(
            model = trackModel.pictureUrl,
            contentDescription = "Track Cover",
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 24.dp)
        )

        // Исполнитель
        Text(
            text = trackModel.author,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        // Название трека
        Text(
            text = trackModel.name,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TrackScreenPreview() {
    MyApplicationTheme {
        TrackScreenContent(
            trackModel = TrackModel(
                id = "1",
                name = "Smells Like Teen Spirit",
                author = "Nirvana",
                pictureUrl = "https://via.placeholder.com/300"
            )
        )
    }
}
