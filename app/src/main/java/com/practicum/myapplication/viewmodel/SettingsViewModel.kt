package com.practicum.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class SettingsViewModel : ViewModel() {

    // Сейчас пусто, позже добавим логику для настроек

    companion object {
        fun getViewModelFactory() = viewModelFactory {
            initializer {
                SettingsViewModel()
            }
        }
    }
}
