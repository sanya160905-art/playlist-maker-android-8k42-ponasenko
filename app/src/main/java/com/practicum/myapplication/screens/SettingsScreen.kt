package com.practicum.myapplication.screens

import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.core.net.toUri
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.myapplication.R
import com.practicum.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit
) {
    val themePreferences = remember { com.practicum.myapplication.creator.Creator.getThemePreferences() }
    val isDarkTheme by themePreferences.isDarkTheme.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
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
                    contentDescription = stringResource(id = R.string.button_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )

            }

            Text(
                text = stringResource(id = R.string.settings_title),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Dark theme toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.dark_theme),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { newValue ->
                    coroutineScope.launch {
                        themePreferences.setDarkTheme(newValue)
                    }
                },
                modifier = Modifier.size(width = 50.dp, height = 24.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 0.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp
        )

        val shareTitle = stringResource(id = R.string.share_app)
        val shareMessage = stringResource(id = R.string.share_message)

        SettingsMenuItemWithIcon(
            title = shareTitle,
            icon = Icons.Default.Share
        ) {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareMessage)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(shareIntent, null))
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 0.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp
        )

        val writeToSupportTitle = stringResource(id = R.string.write_to_support)
        val supportEmail = stringResource(id = R.string.support_email)
        val emailSubject = stringResource(id = R.string.email_subject)
        val emailBody = stringResource(id = R.string.email_body)

        SettingsMenuItemWithIcon(
            title = writeToSupportTitle,
            icon = Icons.Default.Email
        ) {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, arrayOf(supportEmail))
                putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                putExtra(Intent.EXTRA_TEXT, emailBody)
            }
            context.startActivity(emailIntent)
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 0.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp
        )

        val userAgreementTitle = stringResource(id = R.string.user_agreement)
        val offerUrl = stringResource(id = R.string.practicum_offer_url)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    val browserIntent = Intent(Intent.ACTION_VIEW, offerUrl.toUri())
                    context.startActivity(browserIntent)
                }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userAgreementTitle,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = ">",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MyApplicationTheme {
        SettingsScreen(onBackClick = {})
    }
}