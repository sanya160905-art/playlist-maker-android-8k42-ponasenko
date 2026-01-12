package com.practicum.myapplication.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.R
import com.practicum.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.core.net.toUri

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {}
) {
    val isDarkTheme = remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                onClick = onBackClick,
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
                text = stringResource(id = R.string.settings_title),
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Темная тема (с Toggle Switch)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.dark_theme),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = isDarkTheme.value,
                onCheckedChange = { isDarkTheme.value = it },
                modifier = Modifier.size(width = 50.dp, height = 24.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 0.dp),
            color = Color(0xFFE0E0E0),
            thickness = 1.dp
        )

        // Поделиться приложением
        SettingsMenuItemWithIcon(
            title = stringResource(id = R.string.share_app),
            icon = Icons.Default.Share,
            onClick = {
                val shareMessage = context.getString(R.string.share_message)
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareMessage)
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(shareIntent, null))
            }
        )

        HorizontalDivider(
            modifier = Modifier.padding(start = 0.dp),
            color = Color(0xFFE0E0E0),
            thickness = 1.dp
        )

        // Написать разработчикам
        SettingsMenuItemWithIcon(
            title = stringResource(id = R.string.write_to_support),
            icon = Icons.Default.Email,
            onClick = {
                val supportEmail = context.getString(R.string.support_email)
                val emailSubject = context.getString(R.string.email_subject)
                val emailBody = context.getString(R.string.email_body)

                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmail))
                    putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                    putExtra(Intent.EXTRA_TEXT, emailBody)
                }
                context.startActivity(emailIntent)
            }
        )

        HorizontalDivider(
            modifier = Modifier.padding(start = 0.dp),
            color = Color(0xFFE0E0E0),
            thickness = 1.dp
        )

        // Пользовательское соглашение
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val url = context.getString(R.string.practicum_offer_url)
                    val browserIntent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(browserIntent)
                }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.user_agreement),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = ">",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun SettingsMenuItemWithIcon(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MyApplicationTheme {
        SettingsScreen()
    }
}
