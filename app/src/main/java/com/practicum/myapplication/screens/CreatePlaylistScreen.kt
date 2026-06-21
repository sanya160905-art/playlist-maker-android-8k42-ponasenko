package com.practicum.myapplication.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.myapplication.creator.Creator
import kotlinx.coroutines.launch
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.compose.ui.platform.LocalContext
import android.content.Intent

@Composable
fun CreatePlaylistScreen(
    onBackClick: () -> Unit,
    onPlaylistCreated: () -> Unit
) {
    var playlistName by remember { mutableStateOf("") }
    var playlistDescription by remember { mutableStateOf("") }
    var coverImageUri by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val playlistsRepository = remember { Creator.getPlaylistsRepository() }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (e: SecurityException) {
            }
            coverImageUri = it
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            imagePickerLauncher.launch("image/*")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .statusBarsPadding()
                .height(56.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Назад",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            Text(
                text = "Новый плейлист",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            imagePickerLauncher.launch("image/*")
                        } else {
                            when {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ) == PackageManager.PERMISSION_GRANTED -> {
                                    imagePickerLauncher.launch("image/*")
                                }
                                else -> {
                                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (coverImageUri != null) {
                    AsyncImage(
                        model = coverImageUri,
                        contentDescription = "Обложка плейлиста",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = com.practicum.myapplication.R.drawable.ic_add_image_placeholder),
                        contentDescription = "Добавить обложку",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = playlistName,
                onValueChange = { playlistName = it },
                label = { Text("Название плейлиста") },
                placeholder = { Text("Введите название", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF3772E7),
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedLabelColor = Color(0xFF3772E7),
                    cursorColor = Color(0xFF3772E7),
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                ),
                singleLine = true
            )

            OutlinedTextField(
                value = playlistDescription,
                onValueChange = { playlistDescription = it },
                label = { Text("Описание") },
                placeholder = { Text("Добавьте описание", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF3772E7),
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedLabelColor = Color(0xFF3772E7),
                    cursorColor = Color(0xFF3772E7),
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                ),
                maxLines = 4
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (playlistName.isNotBlank()) {
                        coroutineScope.launch {
                            playlistsRepository.addNewPlaylist(
                                playlistName,
                                playlistDescription,
                                coverImageUri?.toString()
                            )
                            onPlaylistCreated()
                        }
                    }
                },
                enabled = playlistName.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3772E7),
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "Создать",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}