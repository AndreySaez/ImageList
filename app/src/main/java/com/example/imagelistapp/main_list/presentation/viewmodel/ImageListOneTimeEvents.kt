package com.example.imagelistapp.main_list.presentation.viewmodel

import androidx.annotation.StringRes

sealed interface ImageListOneTimeEvents {
    data class MakeToast(@StringRes val stringRes: Int) : ImageListOneTimeEvents
}