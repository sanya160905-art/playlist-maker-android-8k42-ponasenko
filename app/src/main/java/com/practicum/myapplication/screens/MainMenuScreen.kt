package com.practicum.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.R
import com.practicum.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.luminance

@Composable
fun MainMenuScreen(
    onSearchClick: () -> Unit,
    onPlaylistsClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onSettingsClick: () -> Unit
) {

    val useDarkIcons = MaterialTheme.colorScheme.background.luminance() < 0.5f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3772E7))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .statusBarsPadding()
        ) {
            Text(
                text = stringResource(id = R.string.main_menu_title),
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(0.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                MenuItem(
                    icon = Icons.Default.Search,
                    title = stringResource(id = R.string.menu_search),
                    onClick = onSearchClick
                )
                MenuDivider()

                MenuItem(
                    iconPainter = painterResource(
                        id = if (useDarkIcons) R.drawable.ic_playlists_custom_dark else R.drawable.ic_playlists_custom
                    ),
                    title = stringResource(id = R.string.menu_playlists),
                    onClick = onPlaylistsClick
                )
                MenuDivider()

                MenuItem(
                    iconPainter = painterResource(
                        id = if (useDarkIcons) R.drawable.ic_favorites_custom_dark else R.drawable.ic_favorites_custom
                    ),
                    title = stringResource(id = R.string.menu_favorites),
                    onClick = onFavoritesClick
                )
                MenuDivider()

                MenuItem(
                    icon = Icons.Default.Settings,
                    title = stringResource(id = R.string.menu_settings),
                    onClick = onSettingsClick
                )
            }
        }
    }
}

// Версия с ImageVector (для системных иконок)
@Composable
fun MenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    MenuItemContent(
        iconContent = {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        title = title,
        onClick = onClick
    )
}

// Версия с Painter (для кастомных PNG-иконок из Figma)
@Composable
fun MenuItem(
    iconPainter: Painter,
    title: String,
    onClick: () -> Unit
) {
    MenuItemContent(
        iconContent = {
            Icon(
                painter = iconPainter,
                contentDescription = title,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
        },
        title = title,
        onClick = onClick
    )
}

@Composable
private fun MenuItemContent(
    iconContent: @Composable () -> Unit,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        iconContent()
        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = stringResource(id = R.string.arrow_button),
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
    }
}

@Composable
fun MenuDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(start = 56.dp),
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 1.dp
    )
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    MyApplicationTheme {
        MainMenuScreen(
            onSearchClick = {},
            onPlaylistsClick = {},
            onFavoritesClick = {},
            onSettingsClick = {}
        )
    }
}