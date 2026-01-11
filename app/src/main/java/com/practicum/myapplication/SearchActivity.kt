package com.practicum.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.practicum.myapplication.screens.SearchScreen
import com.practicum.myapplication.ui.theme.MyApplicationTheme
import com.practicum.myapplication.viewmodel.SearchViewModel

class SearchActivity : ComponentActivity() {

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.getViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                SearchScreen(
                    onBackClick = { finish() },
                    viewModel = viewModel
                )
            }
        }
    }
}
