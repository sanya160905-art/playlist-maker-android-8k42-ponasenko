package com.practicum.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistsScreen(
    onBackClick: () -> Unit
) {
    // Стейт для отображения BottomSheet панели[cite: 2]
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Плейлисты") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        },
        // Добавляем FloatingActionButton, как в практическом задании[cite: 2]
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBottomSheet = true }, // При нажатии открываем панель[cite: 2]
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Создать плейлист")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Список плейлистов пуст")
        }

        // Вызываем ModalBottomSheet, если стейт true[cite: 2]
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false }, // Скрываем при тапе мимо[cite: 2]
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Тут в будущем будет создание нового плейлиста!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { showBottomSheet = false }) {
                        Text("Закрыть")
                    }
                }
            }
        }
    }
}