package com.example.imagelistapp.main_list.presentation.viewmodel

sealed interface Action {
    data object OnScrolledToEnd : Action
    data object Reload : Action
}