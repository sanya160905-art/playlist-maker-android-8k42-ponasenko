package com.practicum.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MainViewModel : ViewModel() {

    // Сейчас пусто, позже добавим логику для главного экрана

    companion object {
        fun getViewModelFactory() = viewModelFactory {
            initializer {
                MainViewModel()
            }
        }
    }
}
